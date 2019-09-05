#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#define TRUE 1
#define FALSE 0

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, connect_fd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[256];
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    
    portno = atoi(argv[2]);
    
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    
    if (sockfd < 0){
        error("ERROR opening socket");
    }
        
    server = gethostbyname(argv[1]);
    
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);

    serv_addr.sin_port = htons(portno);
    connect_fd = connect(sockfd,&serv_addr,sizeof(serv_addr));
    
    if (connect_fd < 0){
        error("ERROR connecting");
    }
    
    while(TRUE){
        
        printf("Ingrese un mensaje ");
        bzero(buffer,256);
        fgets(buffer,255,stdin);

        n = write(sockfd,buffer,strlen(buffer));
        if (n < 0){
            error("Error escribiendo el socket");
        }
        n = recv(sockfd, buffer, sizeof(buffer), 0);
        if (n < 0){
            error("Error leyendo datos desde el socket"); 
        }
        printf("He recibido: %s\n",buffer);
    }
    close(sockfd);
    return 0;
}
