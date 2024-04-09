#ifndef __ZMQ_ROUTER_CLIENT_H__
#define __ZMQ_ROUTER_CLIENT_H__

int zmq_route_client_init(const char *client_name, const char *server_name, const char *end_point);

int zmq_route_client_send_sync(const char *send_msg, int send_msg_len, char *recv_msg, int *recv_msg_len);

int zmq_route_client_deinit();
#endif