//
// Created by henry.xue on 2024-04-02.
//

#include <jni.h>
#include <string>
#include <android/log.h>
#define TAG    "henry"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

extern "C"
JNIEXPORT void JNICALL
Java_com_henry_cmaketest_jniActivity_test2(JNIEnv *env, jobject thiz) {
    LOGD("-------------new cpp test");
}