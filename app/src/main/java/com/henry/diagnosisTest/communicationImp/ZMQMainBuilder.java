package com.henry.diagnosisTest.communicationImp;

import android.util.Log;

import com.google.gson.Gson;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationDefinition;

import java.util.HashMap;

/**
 *
 * 与底层ZMQ通信方式
 */
public class ZMQMainBuilder extends CommunicationBuilderBase {


    private String ipAndProt;


    //private HashMap<String, String> paramMap;
    private String paramMap;

    //private ZMQ.Context mZContext = ZMQ.context(1);
    //
    //private ZMQ.Socket socket;

    private static String zmq_diag_client_name = "net_diag_cmd_client"+"\0";
    private static String zmq_diag_server_name = "net_diag_cmd_server"+"\0";

    public static String TAG = "wangyong";

    public String result = new String();

    public JniZmq socket;

    static {
        System.loadLibrary("native-lib");
    }


    /**
     * 初始化参数
     *
     * @param communicationDefinition   通信定义
     * @param ipAndProt   ip和端口
     * @param paramMap    map参数
     */
    public ZMQMainBuilder(CommunicationDefinition communicationDefinition, String ipAndProt, /**HashMap<String, String>**/String paramMap) {
        super(communicationDefinition);
        this.ipAndProt = ipAndProt;
        this.paramMap = paramMap;
        socket = JniZmq.getInstance();
    }

    /**
     * 提供具体的通信动作
     * 返回 本地 JSONObject 对象
     */
    public String getCommunicationAction() {
        //socket = mZContext.socket(SocketType.ROUTER);
        //socket.setReceiveTimeOut(10000);
        //socket.setSendTimeOut(10000);
        //socket.setIdentity(zmq_diag_client_name.getBytes(ZMQ.CHARSET));//设置标识
        //Log.i("wangyong","client 标志设置成功");
        //boolean is = socket.connect(ipAndProt);
        //Log.i("wangyong","client ip address: "+ipAndProt);
        //Log.i("wangyong","requset 开始");
        //
        //socket.sendMore(zmq_diag_server_name.getBytes(ZMQ.CHARSET));
        //socket.sendMore(zmq_diag_empty.getBytes(ZMQ.CHARSET));
        //socket.send((map2JsonString(paramMap)+"\0").getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT);
        //
        //String s1 = socket.recvStr(0);
        //String s2 = socket.recvStr(0);
        //String s3 = socket.recvStr(0);
        //Log.i("wangyong",s1); //recvstr soa-server
        //Log.i("wangyong",s2); //recvstr null
        //Log.i("wangyong",s3); //recvstr hello

        //add by sam
        //ZMsg zMsg = new ZMsg();
        //zMsg.add(zmq_diag_server_name.getBytes(ZMQ.CHARSET));
        //zMsg.send(socket);
        //ZMsg reply = ZMsg.recvMsg(socket);
        //Log.d("wangyong", "test:"+reply);
        //Flclient3 flclient3 = new Flclient3();
        //Flclient3.main(new String[3]);



        //Log.i("wangyong","request 1 长度:"+zmq_diag_server_name.getBytes(ZMQ.CHARSET).length);
        //boolean a = socket.send(zmq_diag_server_name.getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT|ZMQ.SNDMORE);
        ////boolean a = socket.send(zmq_diag_server_name.getBytes(ZMQ.CHARSET),0,zmq_diag_server_name.getBytes(ZMQ.CHARSET).length,ZMQ.DONTWAIT|ZMQ.SNDMORE);
        ////socket.send(zmq_diag_server_name.getBytes(StandardCharsets.UTF_8));
        //Log.i("wangyong","发送1是否成功：" + a);
        //String str_server = socket.recvStr(0);
        //Log.i("wangyong","发送1 response：" + str_server);
        //
        //
        //Log.i("wangyong","request 2 长度:"+zmq_diag_empty.getBytes(ZMQ.CHARSET).length);
        //boolean b = socket.send(zmq_diag_empty.getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT|ZMQ.SNDMORE);
        ////boolean b = socket.send(zmq_diag_empty.getBytes(ZMQ.CHARSET),0,zmq_diag_empty.getBytes(ZMQ.CHARSET).length,ZMQ.DONTWAIT|ZMQ.SNDMORE);
        ////socket.send(zmq_diag_empty.getBytes(StandardCharsets.UTF_8));
        //Log.i("wangyong","发送2是否成功：" + b);
        //String str_emtpty = socket.recvStr(0);
        //Log.i("wangyong","发送2 response：" + str_emtpty);
        //
        //
        //Log.i("wangyong","request 3 长度:"+map2JsonString(paramMap).getBytes(ZMQ.CHARSET).length);
        ////Log.i("wangyong","request 3："+map2JsonString(paramMap));
        //boolean c = socket.send((map2JsonString(paramMap)+"\0").getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT);
        ////boolean c = socket.send((map2JsonString(paramMap)+"\0").getBytes(ZMQ.CHARSET),0,(map2JsonString(paramMap)+"\0").getBytes(ZMQ.CHARSET).length,ZMQ.DONTWAIT);//发送真实请求
        //Log.i("wangyong","发送3是否成功：" + c);
        ////int result = socket.recv(msg,0,msg.length,ZMQ.DONTWAIT);
        //String result = socket.recvStr(0);
        //Log.i("wangyong","3 response：" + result);

        //socket.close();


        /**
         * socket 初始化、发送、销毁。返回发送过程返回的结果。
         */
        //todo 暂时省略
//        socket.init(zmq_diag_client_name,zmq_diag_server_name,ipAndProt);
//        result = socket.send(/**map2JsonString(paramMap)**/paramMap,(/**map2JsonString(paramMap)**/paramMap+"\0").length());
//        Log.d(TAG, "result:"+result);
//        socket.destory();
        return result;
    }

    private String map2JsonString(HashMap<String, String> paramMap) {
        return new Gson().toJson(paramMap).replace("\\","");
    }
}
