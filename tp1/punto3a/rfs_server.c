/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "rfs.h"


int * rfs_open_1_svc(open_record *argp, struct svc_req *rqstp){
        static int result;  
        printf("llamada open\n");
        result = open(argp->file_name, argp->flags);
        return &result;
}


file_data * rfs_read_1_svc(read_record *argp, struct svc_req *rqstp){
    static file_data result;
    int n;
    printf("llamada read\n");
    result.file_data_val = (char *) malloc(argp->count);
    if (result.file_data_val == 0){
        result.file_data_len = 0;
    }        
    else{
        result.file_data_len = read(argp->fd, result.file_data_val, argp->count);
    }
    return &result;
}


int * rfs_close_1_svc(int *argp, struct svc_req *rqstp){
    static int result;
    printf("llamada close\n");
    result = close(*argp);
    return &result;
}


int * rfs_write_1_svc(write_record *argp, struct svc_req *rqstp){
	static int  result;
    printf("llamada escribir\n");
    printf(argp->buf.file_data_val);
	
    write(argp->fd, argp->buf.file_data_val, strlen(argp->buf.file_data_val));

	return &result;
}
