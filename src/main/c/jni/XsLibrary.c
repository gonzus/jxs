#include <xs/xs.h>

#include "io_crossroads_jni_XsLibrary.h"

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1version(JNIEnv* env,
                                                                    jobject cls,
                                                                    jobject major,
                                                                    jobject minor,
                                                                    jobject patch)
{
    jclass c;
    jfieldID id;
    int cmaj, cmin, cpat;
    
    c = (*env)->FindClass(env, "java/lang/Integer");
    if (c == 0) {
        // LOGD("FindClass failed");
        return -1;
    }

    id = (*env)->GetFieldID(env, c, "value", "I");
    if (id == 0) {
        // LOGD("GetFiledID failed");
        return -1;
    }

    xs_version(&cmaj, &cmin, &cpat);
    (*env)->SetIntField(env, major, id, cmaj);
    (*env)->SetIntField(env, minor, id, cmin);
    (*env)->SetIntField(env, patch, id, cpat);

    return (cmaj*100+cmin)*100+cpat;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1errno(JNIEnv* env,
                                                                  jobject cls)
{
    return xs_errno();
}

JNIEXPORT jstring JNICALL Java_io_crossroads_jni_XsLibrary_xs_1strerror(JNIEnv* env,
                                                                        jobject cls,
                                                                        jint errnum)
{
    const char* err = xs_strerror(errnum);
    jstring answer = (*env)->NewStringUTF(env, err);
    return answer;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1init(JNIEnv* env,
                                                                  jobject cls)
{
    void* cont = xs_init();
#if 0
    fprintf(stderr, "xs_init() => %p\n", cont);
#endif
    return cont;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1term(JNIEnv* env,
                                                                 jobject cls,
                                                                 jlong context)

{
    void* cont = (void*) context;
    int ret = xs_term(cont);
#if 0
    fprintf(stderr, "xs_term(%p) => %d\n", cont, ret);
#endif
    return ret;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1socket(JNIEnv* env,
                                                                    jobject cls,
                                                                    jlong context,
                                                                    jint type)
{
    void* cont = (void*) context;
    void* sock = xs_socket(cont, type);
#if 0
    fprintf(stderr, "xs_socket(%p, %d) => %p\n", cont, type, sock);
#endif
    return sock;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1close(JNIEnv* env,
                                                                  jobject cls,
                                                                  jlong socket)
{
    void* sock = (void*) socket;
    int ret = xs_close(sock);
#if 0
    fprintf(stderr, "xs_close(%p) => %d\n", sock, ret);
#endif
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1bind(JNIEnv* env,
                                                                 jobject cls,
                                                                 jlong socket,
                                                                 jstring address)
{
    void* sock = (void*) socket;
    const char* addr = (*env)->GetStringUTFChars(env, address, NULL);
    int ret = xs_bind(sock, addr);
#if 0
    fprintf(stderr, "xs_bind(%p, %s) => %d\n", sock, addr, ret);
#endif
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1connect(JNIEnv* env,
                                                                    jobject cls,
                                                                    jlong socket,
                                                                    jstring address)
{
    void* sock = (void*) socket;
    const char* addr = (*env)->GetStringUTFChars(env, address, NULL);
    int ret = xs_connect(sock, addr);
#if 0
    fprintf(stderr, "xs_bind(%p, %s) => %d\n", sock, addr, ret);
#endif
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1shutdown(JNIEnv* env,
                                                                     jobject cls,
                                                                     jlong socket,
                                                                     jint how)
{
    void* sock = (void*) socket;
    int ret = xs_shutdown(sock, how);
#if 0
    fprintf(stderr, "xs_shutdown(%p, %d) => %d\n", sock, how, ret);
#endif
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1send(JNIEnv* env,
                                                                 jobject cls,
                                                                 jlong socket,
                                                                 jobject buffer,
                                                                 jint offset,
                                                                 jint length,
                                                                 jint flags)
{
    void* sock = 0;
    jbyte* buf = 0;
    int ret = 0;
    
    sock = (void*) socket;
    buf = (jbyte*) (*env)->GetDirectBufferAddress(env, buffer);
    ret = xs_send(sock, buf, length, flags);

#if 0
    fprintf(stderr, "xs_send(%p, %p, %d, %d) => %d\n",
            sock, buf, length, flags, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1recv(JNIEnv* env,
                                                                 jobject cls,
                                                                 jlong socket,
                                                                 jobject buffer,
                                                                 jint offset,
                                                                 jint length,
                                                                 jint flags)
{
    void* sock = 0;
    jbyte* buf = 0;
    int ret = 0;

    sock = (void*) socket;
    buf = (jbyte*) (*env)->GetDirectBufferAddress(env, buffer);
    ret = xs_recv(sock, buf, length, flags);

#if 0
    fprintf(stderr, "xs_recv(%p, %p, %d, %d) => %d\n",
            sock, buf, length, flags, ret);
#endif
    
    return ret;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1stopwatch_1start(JNIEnv* env,
                                                                              jobject cls)
{
    void* w = xs_stopwatch_start();
#if 0
    fprintf(stderr, "xs_stopwatch_start() => %p\n",
            w);
#endif
    return (jlong) w;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1stopwatch_1stop(JNIEnv* env,
                                                                             jobject cls,
                                                                             jlong watch)
{
    void* w = (void*) watch;
    long ret = xs_stopwatch_stop(w);
#if 0
    fprintf(stderr, "xs_stopwatch_stop(%p) => %ld\n",
            w, ret);
#endif
    return ret;
}
