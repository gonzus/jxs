#include "XsUtil.h"

#include "io_crossroads_jni_XsLibrary.h"

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1version(JNIEnv* env,
                                                                    jobject obj,
                                                                    jobject major,
                                                                    jobject minor,
                                                                    jobject patch)
{
    jclass cmaj = 0;
    jclass cmin = 0;
    jclass cpat = 0;
    jfieldID fmaj = 0;
    jfieldID fmin = 0;
    jfieldID fpat = 0;
    int imaj = 0;
    int imin = 0;
    int ipat = 0;
    
    cmaj = (*env)->GetObjectClass(env, major);
    XS_ASSERT(cmaj);

    fmaj = (*env)->GetFieldID(env, cmaj, "value", "I");
    XS_ASSERT(fmaj);

    cmin = (*env)->GetObjectClass(env, minor);
    XS_ASSERT(cmin);

    fmin = (*env)->GetFieldID(env, cmin, "value", "I");
    XS_ASSERT(fmin);

    cpat = (*env)->GetObjectClass(env, patch);
    XS_ASSERT(cpat);

    fpat = (*env)->GetFieldID(env, cpat, "value", "I");
    XS_ASSERT(fpat);

    xs_version(&imaj, &imin, &ipat);
    (*env)->SetIntField(env, major, fmaj, imaj);
    (*env)->SetIntField(env, minor, fmin, imin);
    (*env)->SetIntField(env, patch, fpat, ipat);

    return (imaj*100+imin)*100+ipat;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1errno(JNIEnv* env,
                                                                  jobject obj)
{
    return xs_errno();
}

JNIEXPORT jstring JNICALL Java_io_crossroads_jni_XsLibrary_xs_1strerror(JNIEnv* env,
                                                                        jobject obj,
                                                                        jint errnum)
{
    const char* err = 0;
    jstring answer =  0;
    
    err = xs_strerror(errnum);
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "Got error for %d: [%s]\n",
            errnum, err);
#endif
    answer = (*env)->NewStringUTF(env, err);
    XS_ASSERT(answer);
    return answer;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1init(JNIEnv* env,
                                                                  jobject obj)
{
    void* cont = 0;

    cont = xs_init();

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_init() => %p\n",
            cont);
#endif

    return cont;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1term(JNIEnv* env,
                                                                 jobject obj,
                                                                 jlong context)

{
    void* cont = 0;
    int ret = 0;

    cont = (void*) context;
    XS_ASSERT(cont);
    ret = xs_term(cont);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_term(%p) => %d\n",
            cont, ret);
#endif
    
    return ret;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1socket(JNIEnv* env,
                                                                    jobject obj,
                                                                    jlong context,
                                                                    jint type)
{
    void* cont = 0;
    void* sock =  0;

    cont = (void*) context;
    XS_ASSERT(cont);
    sock = xs_socket(cont, type);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_socket(%p, %d) => %p\n",
            cont, type, sock);
#endif
    
    return sock;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1close(JNIEnv* env,
                                                                  jobject obj,
                                                                  jlong socket)
{
    void* sock = 0;
    int ret = 0;
    
    sock = (void*) socket;
    XS_ASSERT(sock);
    ret = xs_close(sock);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_close(%p) => %d\n",
            sock, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1bind(JNIEnv* env,
                                                                 jobject obj,
                                                                 jlong socket,
                                                                 jstring address)
{
    void* sock = 0;
    const char* addr = 0;
    int ret = 0;

    sock = (void*) socket;
    XS_ASSERT(sock);
    addr = (*env)->GetStringUTFChars(env, address, NULL);
    XS_ASSERT(addr);
    ret = xs_bind(sock, addr);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_bind(%p, %s) => %d\n",
            sock, addr, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1connect(JNIEnv* env,
                                                                    jobject obj,
                                                                    jlong socket,
                                                                    jstring address)
{
    void* sock = 0;
    const char* addr = 0;
    int ret = 0;

    sock = (void*) socket;
    XS_ASSERT(sock);
    addr = (*env)->GetStringUTFChars(env, address, NULL);
    XS_ASSERT(addr);
    ret = xs_connect(sock, addr);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_bind(%p, %s) => %d\n",
            sock, addr, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1shutdown(JNIEnv* env,
                                                                     jobject obj,
                                                                     jlong socket,
                                                                     jint how)
{
    void* sock = 0;
    int ret = 0;

    sock = (void*) socket;
    XS_ASSERT(sock);
    ret = xs_shutdown(sock, how);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_shutdown(%p, %d) => %d\n",
            sock, how, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1send(JNIEnv* env,
                                                                 jobject obj,
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
    XS_ASSERT(sock);
    buf = (jbyte*) (*env)->GetDirectBufferAddress(env, buffer);
    XS_ASSERT(buf);
    ret = xs_send(sock, buf, length, flags);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_send(%p, %p, %d, %d) => %d\n",
            sock, buf, length, flags, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1recv(JNIEnv* env,
                                                                 jobject obj,
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
    XS_ASSERT(sock);
    buf = (jbyte*) (*env)->GetDirectBufferAddress(env, buffer);
    XS_ASSERT(buf);
    ret = xs_recv(sock, buf, length, flags);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_recv(%p, %p, %d, %d) => %d\n",
            sock, buf, length, flags, ret);
#endif
    
    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsLibrary_xs_1getsockopt_1int(JNIEnv* env,
                                                                 jobject obj,
                                                                 jlong socket,
                                                                 jint option,
                                                                 jobject optval)
{
    void* sock = 0;
    jbyte* buf = 0;
    int ret = 0;
    int ioptval = 0;
    jclass coptval = 0;
    jfieldID foptval = 0;
    size_t sz = sizeof(ioptval);

    coptval = (*env)->GetObjectClass(env, optval);
    XS_ASSERT(coptval);

    foptval = (*env)->GetFieldID(env, coptval, "value", "I");
    XS_ASSERT(foptval);

    sock = (void*) socket;
    XS_ASSERT(sock);

    ret = xs_getsockopt(sock, option, &ioptval, &sz);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_getsockopt(%p, %d, %p, %d) => %d\n",
            sock, option, buf, optval, ret);
#endif

    (*env)->SetIntField(env, optval, foptval, ioptval);

    return ret;
}


JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1stopwatch_1start(JNIEnv* env,
                                                                              jobject obj)
{
    void* wtch = 0;

    wtch = xs_stopwatch_start();
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_stopwatch_start() => %p\n",
            wtch);
#endif
    
    return (jlong) wtch;
}

JNIEXPORT jlong JNICALL Java_io_crossroads_jni_XsLibrary_xs_1stopwatch_1stop(JNIEnv* env,
                                                                             jobject obj,
                                                                             jlong watch)
{
    void* wtch = 0;
    long ret = 0;

    wtch = (void*) watch;
    XS_ASSERT(wtch);
    ret = xs_stopwatch_stop(wtch);
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_stopwatch_stop(%p) => %ld\n",
            wtch, ret);
#endif
    
    return ret;
}
