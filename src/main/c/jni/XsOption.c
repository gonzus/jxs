#include "XsUtil.h"

#include "io_crossroads_jni_XsOption.h"

JNIEXPORT void JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1int(JNIEnv* env,
                                                                             jobject obj,
                                                                             jint option,
                                                                             jint value)
{
}

JNIEXPORT void JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1long(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint option,
                                                                              jlong value)
{
}

JNIEXPORT void JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1binary(JNIEnv* env,
                                                                                jobject obj,
                                                                                jint option,
                                                                                jobjectArray value)
{
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1int(JNIEnv* env,
                                                                             jobject obj,
                                                                             jint option)
{
    return 0;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1long(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint option)
{
    return 0;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1binary(JNIEnv* env,
                                                                                jobject obj,
                                                                                jint option)
{
    return 0;
}
