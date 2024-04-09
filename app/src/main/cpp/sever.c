#include <stdio.h>
#include <pthread.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <strings.h>

#define BUFSIZE 1024

char buffer_send2[BUFSIZE] = "connect stablish!";
char buffer_recv2[BUFSIZE] = {0};

// send to client
void *sendMsg2(void *socket) {
    int *client_socket = (int *) socket;
    while (1) {
        // send data to client
        scanf("%s", buffer_send2);
        printf("sever send message: %s",buffer_send2);
        send(*client_socket, buffer_send2, strlen(buffer_send2), 0);

        // is exit
        if (!strncasecmp(buffer_send2, "quit", 4)) {
            break;
        }
    }
    pthread_exit(NULL);
}

// recv from client
void *recvMsg2(void *socket) {
    int *client_socket = (int *) socket;
    while (1) {
        // clear buffer_recv
        bzero(buffer_recv2, BUFSIZE);

        // read data from client
        if (recv(*client_socket, buffer_recv2, BUFSIZE, 0) > 0) {
            printf("Message from client:%s\n", buffer_recv2);
        }

        // is exit
        if (!strncasecmp(buffer_send2, "quit", 4)) {
            break;
        }
    }
    pthread_exit(NULL);
}


int sever() {
    // create socket
    int server_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);//
    // socket函数用于创建套接字，指定地址族为IPv4，类型为流式套接字，协议为TCP

    // bind ip port to socket  绑定ip和端口到套接字
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));// use zero fill
    server_addr.sin_family = AF_INET;// use IPV4 address
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");// ip
    server_addr.sin_port = htons(6666); // port
    bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr));
    //bind函数将IP地址和端口绑定到套接字上

    // listen // 监听连接请求
    listen(server_socket, 20);
    //listen函数开始监听连接请求，第二个参数指定同时处理的最大连接数

    // accept client request  // 接受客户端请求
    struct sockaddr_in client_addr;
    socklen_t client_addr_size = sizeof(client_addr);
    int client_socket = accept(server_socket, (struct sockaddr *) &client_addr, &client_addr_size);
    /**
     * accept()函数接受客户端连接请求，返回一个新的套接字用于与客户端通信
     - server_socket: 服务器套接字描述符
      - server_addr: 服务器地址结构体，包含IP地址和端口信息
     - client_addr: 客户端地址结构体，用于存储客户端的地址信息
     - client_addr_size: 传入客户端地址结构体的大小，接收accept函数填充后的客户端地址结构体大小
      - client_socket: 与客户端通信的套接字描述符
     */

    // send stablish info to client
    send(client_socket, buffer_send2, strlen(buffer_send2), 0);
    /**
     * send函数详解：
    函数原型：ssize_t send(int sockfd, const void *buf, size_t len, int flags);
    参数详解：
    - sockfd: 套接字描述符，用于发送数据的套接字
    - buf: 要发送的数据的缓冲区指针
    - len: 要发送的数据的长度
    - flags: 通常为0，可以指定一些控制发送行为的选项
    返回值：成功时返回发送的字节数，出错时返回-1
     */



    // create send and recv thread
    pthread_t send_thread, recv_thread;

    pthread_create(&send_thread, NULL, sendMsg2, (void *) &client_socket);
    pthread_create(&recv_thread, NULL, recvMsg2, (void *) &client_socket);

    // wait send and recv thread over
    pthread_join(send_thread, NULL);
    pthread_join(recv_thread, NULL);

    // close
    close(client_socket);
    close(server_socket);

    return 0;
}
