/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#define TRUE 1
#define FALSE 0

void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
    int sockfd, newsockfd, portno, clilen;
    char buffer[256];
    struct sockaddr_in serv_addr, cli_addr;
    int n;
    if (argc < 2) {
        fprintf(stderr,"ERROR, no port provided\n");
        exit(1);
    }
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0){
        error("ERROR opening socket");
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    portno = atoi(argv[1]);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);
    
    if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0){
        error("ERROR on binding");  
    }      
        ;

        while (TRUE){
            listen(sockfd,5);
            clilen = sizeof(cli_addr);
            newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);

            if (newsockfd < 0){
                error("ERROR on accept");
            }
            printf("pase el accep\n");
            bzero(buffer,256);

            //aca va el codigo
            while ((n = recv(newsockfd, buffer, sizeof(buffer), 0)) > 0){
                write(newsockfd, buffer, n);
            }
            close(newsockfd);
        }

        close(newsockfd);
    
    return 0; 
}

/*
            n = read(newsockfd,buffer,255);
            if (n < 0){
                error("Error leyendo datos desde el socket"); 
            }
            printf("He recibido: %s\n",buffer);
            n = write(newsockfd,"",18);
            if (n < 0){
                error("Error escribiendo el socket");
            }
            
 */