#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <stdarg.h>
#include <unistd.h>
#include <stdint.h>
#include <android/log.h>

#include "zmq.h"
#include "zmq_router_client.h"

#define LOG_TAG "wangyong"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__);

typedef struct
{
    void *context;
    void *router;
    char client_name[100];
    char server_name[100];
    char end_port[100];
} zmq_route_router_internal;

static zmq_route_router_internal *p_zmq_router = NULL;

int zmq_route_client_init(const char *client_name, const char *server_name, const char *end_point)
{
    int rc = 0, ret = 0;
    do
    {
        if (client_name == NULL || server_name == NULL || end_point == NULL)
        {
            LOGE("client_name == NULL || server_name == NULL || end_point == NULL");
            ret = -1;
            break;
        }
        p_zmq_router = malloc(sizeof(zmq_route_router_internal));
        if (p_zmq_router == NULL)
        {
            LOGE("malloc failure");
            ret = -1;
            break;
        }
        memset(p_zmq_router,0x00,sizeof(zmq_route_router_internal));
        strncpy(p_zmq_router->client_name, client_name, strlen(client_name)+1);
        strncpy(p_zmq_router->server_name, server_name, strlen(server_name)+1);
        strncpy(p_zmq_router->end_port, end_point, strlen(end_point)+1);

        p_zmq_router->context = zmq_ctx_new();
        if (p_zmq_router->context == NULL)
        {
            LOGE("zmq_ctx_new failure,error = %s", zmq_strerror(zmq_errno()));
            ret = -1;
            break;
        }
        //创建套接字、连接代理的ROUTER端
        p_zmq_router->router = zmq_socket(p_zmq_router->context, ZMQ_ROUTER);
        if (p_zmq_router->router == NULL)
        {
            LOGE("zmq_socket failure,error = %s", zmq_strerror(zmq_errno()));
            ret = -1;
            break;
        }
        rc = zmq_setsockopt(p_zmq_router->router, ZMQ_ROUTING_ID, p_zmq_router->client_name, strlen(p_zmq_router->client_name) + 1);
        if (rc < 0)
        {
            LOGE("zmq_setsockopt failure,error = %s", zmq_strerror(zmq_errno()));
            ret = -1;
            break;
        }
        rc = zmq_connect(p_zmq_router->router, p_zmq_router->end_port);
        if (rc < 0)
        {
            LOGE("zmq_connect failure,error = %s", zmq_strerror(zmq_errno()));
            zmq_close(p_zmq_router->router);
            zmq_ctx_destroy(p_zmq_router->router);
            ret = -1;
            break;
        }
        free(client_name);
        free(server_name);
        free(end_point);
    } while (0);

    return ret;
}

int zmq_route_client_send_sync(const char *send_msg, int send_msg_len, char *recv_msg, int *recv_msg_len)
{
    int rc = 0, ret = 0;
    char buf[1] = {0};
    int recv_len = 0;
    LOGD("recv_msg_len = %d", *recv_msg_len);
    if (p_zmq_router == NULL || send_msg == NULL || recv_msg == NULL)
    {
        LOGE("p_zmq_router == NULL || send_msg == NULL || recv_msg == NULL");
        return -1;
    }
    sleep(1);
    rc = zmq_send(p_zmq_router->router, p_zmq_router->server_name, strlen(p_zmq_router->server_name) + 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
    LOGD("send addr:%d,%s\n", rc, rc > 0 ? p_zmq_router->server_name : "null");
    if (rc > 0)
    {
        buf[0] = 0;
        rc = zmq_send(p_zmq_router->router, buf, 1, ZMQ_DONTWAIT | ZMQ_SNDMORE);
        LOGD("send data type:%d,%d\n", rc, buf[0]);
        rc = zmq_send(p_zmq_router->router, send_msg, send_msg_len, ZMQ_DONTWAIT);
        LOGD("send data:%d,%s\n", rc, rc > 0 ? buf : "null");
    }
    rc = zmq_recv(p_zmq_router->router, p_zmq_router->server_name, sizeof(p_zmq_router->server_name), 0);
    LOGD("recv addr:%d,%s\n", rc, rc > 0 ? p_zmq_router->server_name : "null");
    if (rc > 0)
    {
        rc = zmq_recv(p_zmq_router->router, buf, sizeof(buf), ZMQ_DONTWAIT);
        LOGD("recv data type:%d,%d\n", rc, buf[0]);
        try_again:
            rc = zmq_recv(p_zmq_router->router, recv_msg + recv_len, *recv_msg_len - recv_len, ZMQ_DONTWAIT);
            LOGD("rc = %d,errno = %d", rc, errno);
            if(rc > 0 && errno == EAGAIN){
                LOGD("EAGAIN");
                recv_len += rc;
                goto try_again;
            }
            *recv_msg_len = recv_len + rc;
    }
    return ret;
}

int zmq_route_client_deinit()
{
    int ret = 0;
    // 6.关闭套接字、销毁上下文
    sleep(1);
    zmq_close(p_zmq_router->router);
    zmq_ctx_destroy(p_zmq_router->context);
    free(p_zmq_router);
    return ret;
}