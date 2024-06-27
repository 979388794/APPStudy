#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include "zmq.h"
#include <android/log.h>
#include <jni.h>


void* callback(void* arg);
void zmq_send_cmd_diag();
char* zmq_recv_cmd_diag(char* buf);
void destory();

#define LOG_TAG "wangyong"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__);

typedef struct diag_cmd
{
    char *name;
    char *cmd;
} diag_cmd_t;

diag_cmd_t diag_cmd_string[] = {{"diag", "{\"cmd\":\"diag\"}"},
                                {"fix", "{\"cmd\":\"fix\"}"},
        /*   {"log_upload", "{\"cmd\":\"log_upload\"}"},
           {"diag_history_upload", "{\"cmd\":\"diag_history_upload\"}"},*/
                                {"start_qxdm", "{\"cmd\":\"start_qxdm\",\"args\":{\"output_dir\":\"/tmp/\",\"file_size\":\"3\",\"file_no\":\"5\",\"extra_args\":\"\"}}"},
                                {"stop_qxdm", "{\"cmd\":\"stop_qxdm\"}"}};

int rc;
const char *client_name = "net_diag_cmd_client";
char server_addr[100] = "net_diag_cmd_server";
char buf[10000] = {0};
void *context;
void *router;

void init()
{
    // 1.初始化上下文
    context = zmq_ctx_new();
    // 2.创建套接字、连接代理的ROUTER端
    router = zmq_socket(context, ZMQ_ROUTER);
    printf("zmq_socket\n");
    rc = zmq_setsockopt(router, ZMQ_ROUTING_ID, client_name, strlen(client_name) + 1);
    printf("zmq_setsockopt\n");
    rc = zmq_connect(router, "tcp://192.168.225.1:5001");
    printf("zmq_connect\n");
    if (rc == -1)
    {
        perror("zmq_connect");
        zmq_close(router);
        zmq_ctx_destroy(context);
    }

    //while (1)
    //{
        sleep(1);
        rc = zmq_send(router, server_addr, strlen(server_addr) + 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
        printf("send addr:%d,%s\n", rc, rc > 0 ? server_addr : "null");
        if (rc > 0)
        {
            buf[0] = 0;
            rc = zmq_send(router, buf, 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
            printf("send data type:%d,%d\n", rc, buf[0]);
            rc = zmq_send(router, diag_cmd_string[0].cmd, strlen(diag_cmd_string[0].cmd) + 1, ZMQ_DONTWAIT);
            printf("send data:%d,%s\n", rc, rc > 0 ? buf : "null");
        }

        rc = zmq_recv (router, server_addr, sizeof(server_addr), 0);
        printf("recv addr:%d,%s\n", rc,rc>0?server_addr:"null");
        if(rc > 0)
        {
            memset(buf, 0, sizeof(buf));
            rc = zmq_recv (router, buf, sizeof(buf), ZMQ_DONTWAIT);
            printf("recv data type:%d,%d\n", rc,buf[0]);
            rc = zmq_recv (router, buf, sizeof(buf), ZMQ_DONTWAIT);
            LOGD("recv data:%d,%s\n", rc,rc>0?buf:"null");
            callback((void*)buf);
        }
    //}

     //6.关闭套接字、销毁上下文
    zmq_close(router);
    zmq_ctx_destroy(context);
}

//void zmq_send_cmd_diag(){
//    sleep(1);
//    rc = zmq_send(router, server_addr, strlen(server_addr) + 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
//    LOGD("send addr:%d,%s\n", rc, rc > 0 ? server_addr : "null");
//    if (rc > 0)
//    {
//        buf[0] = 0;
//        rc = zmq_send(router, buf, 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
//        LOGD("send data type:%d,%d\n", rc, buf[0]);
//        rc = zmq_send(router, diag_cmd_string[0].cmd, strlen(diag_cmd_string[0].cmd) + 1, ZMQ_DONTWAIT);
//        LOGD("send data:%d,%s\n", rc, rc > 0 ? buf : "null");
//    }
//}
//
//char* zmq_recv_cmd_diag(char* buf){
//    sleep(1);
//    rc = zmq_recv (router, server_addr, sizeof(server_addr), 0);
//    LOGD("recv addr:%d,%s\n", rc,rc>0?server_addr:"null");
//    if(rc > 0)
//    {
//        memset(buf, 0, sizeof(buf));
//        rc = zmq_recv (router, buf, sizeof(buf), ZMQ_DONTWAIT);
//        LOGD("recv data type:%d,%d\n", rc,buf[0]);
//        rc = zmq_recv (router, buf, sizeof(buf), ZMQ_DONTWAIT);
//        LOGD("recv data:%d,%s\n", rc,rc>0?buf:"null");
//        callback((void*)buf);
//        return buf;
//    }
//    return NULL;
//}
//
//void destory(){
//    // 6.关闭套接字、销毁上下文
//    zmq_close(router);
//    zmq_ctx_destroy(context);
//}