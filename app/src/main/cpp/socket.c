// Native methods implementation for server and client

#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <android/log.h>
#include <arpa/inet.h>
#include "mylog.h"



// Server side implementation
JNIEXPORT void JNICALL
Java_com_henry_cmaketest_jniActivity_sever(JNIEnv *env, jobject thiz) {
    int server_fd, new_socket;
    struct sockaddr_in address;
    int opt = 1;
    int addrlen = sizeof(address);
    char buffer[1024] = {0};
    const char *hello = "Hello from server";

    // Create socket file descriptor
    if ((server_fd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) == 0) {
        perror("socket failed");
        LOGD("socket failed");
        return;
    }

    // Forcefully attaching socket to the port 8080
    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT,
                   &opt, sizeof(opt))) {
        perror("setsockopt");
        LOGD("setsockopt");
        return;
    }
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr("127.0.0.1");
    address.sin_port = htons(8080);

    // Forcefully attaching socket to the port 8080
    if (bind(server_fd, (struct sockaddr *) &address,
             sizeof(address)) < 0) {
        perror("bind failed");
        LOGD("bind failed");
        return;
    }
    if (listen(server_fd, 3) < 0) {
        perror("listen");
        LOGD("listen");
        return;
    }
    if ((new_socket = accept(server_fd, (struct sockaddr *) &address,
                             (socklen_t *) &addrlen)) < 0) {
        perror("accept");
        LOGD("accept");
        return;
    }
    read(new_socket, buffer, 1024);
    printf("%s\n", buffer);
    LOGD("客户端发送的消息为 %s", buffer);
    send(new_socket, hello, strlen(hello), 0);
    printf("Hello message sent\n");
    LOGD("Hello message sent\n");
    close(new_socket);
    close(server_fd);
}


JNIEXPORT void JNICALL
Java_com_henry_cmaketest_jniActivity_client(JNIEnv *env, jobject thiz) {
    struct sockaddr_in address;
    int sock = 0, valread;
    struct sockaddr_in serv_addr;
    const char *hello = "Hello from client";
    char buffer[1024] = {0};

    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Socket creation error \n");
        LOGD("\n Socket creation error \n");
        return;
    }

    memset(&serv_addr, '0', sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(8080);
    serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");// 服务器IP地址  127.0.0.1本地回环
    // Convert IPv4 and IPv6 addresses from text to binary form

    if (connect(sock, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nConnection Failed \n");
        LOGD("\nConnection Failed \n");
        return;
    }
    send(sock, hello, strlen(hello), 0);
    printf("Hello message sent from client\n");
    LOGD("Hello message sent from client\n");
    valread = read(sock, buffer, 1024);
    printf("%s\n", buffer);
    LOGD("%s\n", buffer);
    close(sock);
}


