/*
 *	clstub.c
 */

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <string.h>

#include "prototipo.h"
#include "strustub.h"

#define MAXBUFFER 16

char buffcom[ MAX_DATA ];
CLSVBUFF clsvbuff;

void error(char *msg)
{
    perror(msg);
    //exit(0);
}

/*
 *INTERFASE CON CAPA TRANSPORTE
*/

static int send_rcv( int s, CLSVBUFF *p, int opcode, int qty )
  {
  int qtyrec;
  p->opc = opcode;
  send_packet( s, p, qty + sizeof( OPC ) );
  qtyrec = receive_packet( s, p, sizeof( *p ) );
  return qtyrec - sizeof( OPC );
  }

//Emisión
int send_packet( int sockfd, const void *p, int qty )
    {
    int n;
    memcpy( buffcom, p, qty );
    n = write(sockfd,buffcom,qty);
    if (n < 0) 
         error("ERROR al escribir socket");
    bzero(buffcom, MAX_DATA);
    return n;
    }

//Recepción
int receive_packet( int sockfd, void *p, int lim )
    {
    int n;
    n = read(sockfd,buffcom,lim);
    if (n < 0) 
         error("ERROR al leer socket");
    else
	{
	memcpy( p, buffcom, n );
	bzero(buffcom, MAX_DATA);
	}
    return n;
    }

//INICIALIZACION
int sockcl_init(char *arg1, char *arg2)
    {
    int sockfd, portno, n;

    struct sockaddr_in serv_addr;
    struct hostent *server;

    portno = atoi(arg2);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");
    server = gethostbyname(arg1);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        return(-1);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,&serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");

    //Una vez conectado, pruebo escribir y leer.
    n = write(sockfd,"Hola Server..",13);
    if (n < 0) 
         error("ERROR writing to socket");
     n = read(sockfd,buffcom,MAX_DATA);
     if (n < 0)
	error("ERROR reading from socket");
     else
	printf("mensaje: %s\n",buffcom);
     bzero(buffcom, MAX_DATA);

   return sockfd;
}

/*
 *CAPA MIDDLEWARE (STUB)
 */

RD rmtopen( int s, short flags, const char *pathname)
  {
  CLSV_ROPEN *pc;
  SVCL_ROPEN *ps;

  pc = &clsvbuff.data.clsv_ropen;
  ps = &clsvbuff.data.svcl_ropen;
  pc->flags = flags;
  //strcpy( pc->flags, flags);
  strcpy( pc->pathname, pathname );
  send_rcv( s, &clsvbuff, ROPEN, sizeof( CLSV_ROPEN ) );

  return ps->rd;
  }

int rmtread( int s, RD rd, void *data, int qty, int binicio )
  {
  CLSV_RREAD *pc;
  SVCL_RREAD *ps;

  int status;

  pc = &clsvbuff.data.clsv_rread;
  ps = &clsvbuff.data.svcl_rread;

  pc->rd = rd;
  pc->qty = qty;
  pc->binicio = binicio;
  send_rcv( s, &clsvbuff, RREAD, sizeof( CLSV_RREAD ) );

  status = ps->status;
  if( status > 0 )
    memcpy( data, ps->data, status );
  return status;
  }

int rmtwrite( int s, RD rd, const void *data, int qty )
  {
  CLSV_RWRITE *pc;
  SVCL_RWRITE *ps;

  pc = &clsvbuff.data.clsv_rwrite;
  ps = &clsvbuff.data.svcl_rwrite;

  pc->rd = rd;
  pc->qty = qty;
  if( qty > 0 )
    memcpy( pc->data, data, qty );
  send_rcv( s, &clsvbuff, RWRITE, sizeof( CLSV_RWRITE ) );

  return ps->status;
  }

int rmtclose( int s, RD rd )
  {
  clsvbuff.data.clsv_rclose.rd = rd;
  send_rcv( s, &clsvbuff, RCLOSE, sizeof( CLSV_RCLOSE ) );
  return clsvbuff.data.svcl_rclose.status;
  }

int remote_read(int s,short flags, const char *pathname,int binicio, int cbytes)
{
  RD rd;
  FILE *f;
  int qty,c_restantes,p_actual;
  char buffer[MAXBUFFER], *to;
  to = "alfa.txt";
  rd = rmtopen(s, flags, pathname);
  if (rd >= 0)
  {
    printf("Abrió el remoto: %d\n", rd);
    f = fopen(to ,"w");
    if( f== NULL || rd<0)
    {
      perror( "leer" );
      exit(1);
    }
    c_restantes = cbytes;
    p_actual = binicio;

    while (c_restantes >0 && qty != 0)
    {
      if (c_restantes < sizeof( buffer ))
        qty = c_restantes;
      else
        qty = sizeof( buffer );
      qty = rmtread ( s, rd, buffer, qty, p_actual);
      fwrite( buffer, 1, qty, f);
      c_restantes -= qty;
      p_actual += qty;
    }
    printf("Cerrando el remoto %d\n", rmtclose(s,rd));
    fclose(f);
  }
  else
    printf("Lo siento..!! NO pudo abrirse el %s remoto.\n",to);
}


int remote_write(int s, short flags, const char *to,  int cbytes)
{
  RD rd;
  FILE *f;
  int qty,c_restantes,p_actual;
	char buffer[ MAXBUFFER ], *from;
  from =  "beta.txt";   //defino origen local de datos

  rd = rmtopen(s, flags, to);
  if (rd>=0)
  {
    printf( "Abrió el remoto: %d\n", rd);
    f = fopen( from, "r" );
    printf("AAAAA\n");
    if( f == NULL || rd < 0 )  //Hay error en aperturas
		{
      printf("BBBB  %d\n",rd);
		  perror( "escribir" );
		  exit( 1 );
    }
    printf("CCCCC\n");
    c_restantes = cbytes;
	  p_actual = 0;
	  while (c_restantes > 0 && qty != 0)
    {
      if (c_restantes < sizeof( buffer ))
		    qty = c_restantes;
	    else
		    qty = sizeof( buffer ); 
	    fseek(f, p_actual, SEEK_SET);
	    qty = fread( buffer, 1, qty, f );
	    rmtwrite( s, rd, buffer, qty );	    
	    c_restantes -= qty;
	    p_actual +=qty;
    }
    printf( "Cerrando el remoto %d\n", rmtclose( s, rd ) );
	  fclose( f );
  }
  else
    printf("Lo siento..!! No pudo abrirse el %s remoto.\n", to );
}



