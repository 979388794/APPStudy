#include <jni.h>
#include <string>
#include <android/log.h>
#include <unistd.h>
#include <pthread.h>







#define TAG    "henry"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))


extern "C" jstring
Java_com_henry_cmaketest_JNIDemo_stringJni(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


/**
 * 参数一：JNIEnv* env表示指向可用JNI函数表的接口指针，所有跟jni相关的操作都需要通过env来完成
 * 参数二：jobject是调用该方法的java对象，这里是MainActivity调用的，所以thiz代表MainActivity
 * 方法名：Java_包名_类名_方法名
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_henry_cmaketest_jniActivity_test(JNIEnv *env, jobject thiz) {
    //获取Activity的class对象
    jclass clazz = env->GetObjectClass(thiz);
    //获取MainActivity中num变量id
    /**
    参数1：MainActivity的class对象
    参数2：变量名称
    参数3：变量类型，具体见上《表3-方法签名》
    **/
    jfieldID numFieldId = env->GetFieldID(clazz, "num", "I");
    //根据变量id获取num的值
    jint oldValue = env->GetIntField(thiz, numFieldId);
    //将num变量的值+1
    env->SetIntField(thiz, numFieldId, oldValue + 1);
    //重新获取num的值
    jint num = env->GetIntField(thiz, numFieldId);
    //先获取tv变量id
    jfieldID tvFieldId = env->GetFieldID(clazz, "tv", "Landroid/widget/TextView;");
    //根据变量id获取textview对象
    jobject tvObject = env->GetObjectField(thiz, tvFieldId);
    //获取textview的class对象
    jclass tvClass = env->GetObjectClass(tvObject);
    //获取setText方法ID
    /**
    参数1：textview的class对象
    参数2：方法名称
    参数3：方法参数类型和返回值类型，具体见上《表3-方法签名》
    **/
    jmethodID methodId = env->GetMethodID(tvClass, "setText", "([CII)V");
    //获取setText所需的参数
    //先将num转化为jstring
    char buf[64];
    sprintf(buf, "%d", num);
    jstring pJstring = env->NewStringUTF(buf);
    const char *value = env->GetStringUTFChars(pJstring, JNI_FALSE);
    //创建char数组，长度为字符串num的长度
    jcharArray charArray = env->NewCharArray(strlen(value));
    //开辟jchar内存空间
    jchar *pArray = (jchar *) calloc(strlen(value), sizeof(jchar));
    //将num字符串缓冲到内存空间中
    for (int i = 0; i < strlen(value); ++i) {
        *(pArray + i) = *(value + i);
    }
    //将缓冲的值写入到上面创建的char数组中
    env->SetCharArrayRegion(charArray, 0, strlen(value), pArray);
    //调用setText方法
    env->CallVoidMethod(tvObject, methodId, charArray, 0, env->GetArrayLength(charArray));
    //释放资源
    env->ReleaseCharArrayElements(charArray, env->GetCharArrayElements(charArray, JNI_FALSE), 0);
    free(pArray);
    pArray = NULL;

}

extern "C" {
int zmq_route_client_init(const char *client_name, const char *server_name, const char *end_point);
int zmq_route_client_send_sync(const char *send_msg, int send_msg_len, char *recv_msg, int *recv_msg_len);
int zmq_route_client_deinit();
}

jstring charTojstring(JNIEnv *env, const char *pat);
char* jstringTochar(JNIEnv* env, jstring jstr);

JavaVM* g_jvm;
jobject g_obj;

extern "C"
JNIEXPORT void JNICALL
Java_com_quectel_cardiagnosis_communicationImp_JniZmq_init(JNIEnv *env, jobject thiz, jstring client_name, jstring server_name, jstring address) {
    // TODO: implement init()
    env->GetJavaVM(&g_jvm);
    g_obj = env->NewGlobalRef(thiz);
    int ret;
    ret = zmq_route_client_init(jstringTochar(env,client_name), jstringTochar(env,server_name), jstringTochar(env,address));
    LOGD("ret :%d,%s" , ret,ret==0?"init sucess":"init failed");

}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_quectel_cardiagnosis_communicationImp_JniZmq_send(JNIEnv *env, jobject thiz, jstring cmd, jint length) {
    // TODO: implement send()
    //char recv_msg[300000] = {0};
    char recv_msg[104857600] = {0};
    int recv_msg_len = sizeof(recv_msg);
    //JNIEnv *mEnv;
    //g_jvm->AttachCurrentThread(&mEnv,NULL);
    zmq_route_client_send_sync(jstringTochar(env,cmd), length, recv_msg, &recv_msg_len);
    return charTojstring(env,recv_msg);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_quectel_cardiagnosis_communicationImp_JniZmq_destory(JNIEnv *env, jobject thiz) {
    // TODO: implement destory()
    zmq_route_client_deinit();
}


//jstring to char
char* jstringTochar(JNIEnv* env, jstring jstr){
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr= (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0)
    {
        rtn = (char*)malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}


//char* to jstring
jstring charTojstring(JNIEnv *env, const char *pat) {
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("java/lang/String");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("utf-8");
    //将byte数组转换为java String,并输出
    return (jstring) (env)->NewObject(strClass, ctorID, bytes, encoding);
}