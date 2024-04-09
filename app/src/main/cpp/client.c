#include <stdio.h>
#include <pthread.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <strings.h>

#define BUFSIZE 1024
 
char buffer_send[BUFSIZE] = {0};
char buffer_recv[BUFSIZE] = {0};
 
// send to server
void * sendMsg(void *socket){
	int * client_socket = (int *)socket;
	while(1){
		// send data to server
		scanf("%s", buffer_send);
		send(*client_socket, buffer_send, strlen(buffer_send), 0);
 
		// is exit
		if(!strncasecmp(buffer_send, "quit", 4)){
			break;
		}
	}
	pthread_exit(NULL);
    /**
     * pthread_exit函数详解：
      // 功能：终止当前线程的执行，并将retval作为线程的返回值
      // retval: 线程的返回值，可以在pthread_join中获取
     */
}
 
// recv from server
void * recvMsg(void *socket){
	int * client_socket = (int *)socket;
	while(1){
		// clear buffer_recv
		bzero(buffer_recv, BUFSIZE);
		
		// read data from client
		if(recv(*client_socket, buffer_recv, BUFSIZE, 0) > 0){
			printf("Message from server:%s\n", buffer_recv);
		}
		// is exit
		if(!strncasecmp(buffer_send, "quit", 4)){
			break;
		}
	}
	pthread_exit(NULL);
}
 
int client(){
	// create socket			// 创建socket
	int client_socket = socket(AF_INET, SOCK_STREAM, 0);
	
	// requset server			// 请求服务器
	struct sockaddr_in server_addr;
	memset(&server_addr, 0, sizeof(server_addr));//初始化为0
	server_addr.sin_family = AF_INET;// 使用IPv4地址族
	server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");// 服务器IP地址  127.0.0.1本地回环
	server_addr.sin_port = htons(6666);// port	服务器端口
	connect(client_socket, (struct sockaddr*)&server_addr, sizeof(server_addr));//连接服务器
	
	//清空buffer_recv
	bzero(buffer_recv, BUFSIZE);			
	
	// recv data from server
	if(recv(client_socket, buffer_recv, BUFSIZE, 0) > 0){
		printf("Message from server:%s\n", buffer_recv); // 打印从服务器接收的消息
	}
 
	// create send and recv thread
	pthread_t send_thread, recv_thread;
	
	pthread_create(&send_thread, NULL, sendMsg, (void *)&client_socket);// 创建发送消息线程
	pthread_create(&recv_thread, NULL, recvMsg, (void *)&client_socket);// 创建接收消息线程
	/**
	 * pthread_create
	 * // 参数详解：
       // - thread: 用于存储新创建线程的ID
       // - attr: 线程属性，一般为NULL表示使用默认属性
       // - start_routine: 新线程的入口函数，线程创建后会执行该函数
       // - arg: 传递给start_routine函数的参数
	 */


	// wait send and recv thread over
	// 等待发送和接收线程结束
	pthread_join(send_thread, NULL);
	pthread_join(recv_thread, NULL);
	/**
	 * pthread_join
	 * 参数详解：
      // - thread: 要等待的线程ID
	  // - retval: 用于获取线程的返回值，如果不关心返回值可以为NULL
	 */

	// close
	close(client_socket);// 关闭客户端socket
	
	return 0;
}
