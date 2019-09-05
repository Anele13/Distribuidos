/*
* programa cliente desarrollado sobre la base del ejemplo que genera rpcgen
*/
#include "rfs.h"
#include "rfs_xdr.c"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

void rfs_1(char *host, char *file_name){

    CLIENT *clnt; int *result_1;
    open_record rfs_open_1_arg;
    file_data *result_2;
    read_record rfs_read_1_arg;
    int *result_3;
    int rfs_close_1_arg;
    int fd, n;

    #ifndef DEBUG
        clnt = clnt_create (host, RFS, RFS_VERS_1, "udp"); if (clnt == NULL) {
        clnt_pcreateerror (host);
        exit (1);
    }
    #endif /* DEBUG */
   
    rfs_open_1_arg.file_name = file_name; /* define archivo a leer */
    rfs_open_1_arg.flags = O_RDWR;
    /* define permisos */
    result_1 = rfs_open_1(&rfs_open_1_arg, clnt);
   
    if (result_1 == (int *) NULL) {
        clnt_perror (clnt, "Fallo llamada open");
    }
    
    fd = *result_1;
    
    if (fd == -1) {
        printf("Error al abrir el archivo\n"); return; 
    }
    
    rfs_read_1_arg.fd = fd;
    rfs_read_1_arg.count = 20;

    do {
        result_2 = rfs_read_1(&rfs_read_1_arg, clnt);
        if (result_2 == (file_data *) NULL) {
            clnt_perror (clnt, "Fallo llamada read");
        }
    
    for (n=0; n < result_2->file_data_len; ++n)
        putchar(result_2->file_data_val[n]);
    } while (result_2->file_data_len == 20);

    putchar('\n');
    result_3 = rfs_close_1(&rfs_close_1_arg, clnt);
    
    if (result_3 == (int *) NULL) {
        clnt_perror (clnt, "fallo llamada close");
    }

    #ifndef DEBUG
        clnt_destroy (clnt);
    #endif /* DEBUG */
    
}

int main (int argc, char *argv[]){
    char *host;
    char *file_name;
    /* Se deben pasar nombre de host y de archivo => argc=3 */
    
    if (argc < 3) {
        printf ("usage: %s server_host filename\n", argv[0]);
        exit (1);
    }
    
    host = argv[1];
    
    /* nombre del host remoto */
    
    file_name = argv[2]; /* nombre del archivo a leer */
    //rfs_1 (host, file_name);
    

    time_t timer;
    char buffer[26];
    struct tm* tm_info;

    time(&timer);
    tm_info = localtime(&timer);

    strftime(buffer, 26, "%Y-%m-%d %H:%M:%S", tm_info);
    puts(buffer);
    
    exit (0);
}
