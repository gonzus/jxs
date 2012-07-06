#include "XsUtil.h"

#include "io_crossroads_jni_XsOption.h"

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1int(JNIEnv* env,
                                                                             jobject obj,
                                                                             jint optidx,
                                                                             jobject optval)
{
    jint ret = -1;

    switch (optidx) {
    case XS_TYPE:
    case XS_RCVMORE:
    case XS_SNDHWM:
    case XS_RCVHWM:
    case XS_RATE:
    case XS_RECOVERY_IVL:
    case XS_SNDBUF:
    case XS_RCVBUF:
    case XS_LINGER:
    case XS_RECONNECT_IVL:
    case XS_RECONNECT_IVL_MAX:
    case XS_BACKLOG:
    case XS_MULTICAST_HOPS:
    case XS_RCVTIMEO:
    case XS_SNDTIMEO:
    case XS_IPV4ONLY:
    case XS_FD: // TODO: should this be a long?
    case XS_EVENTS:
    case XS_KEEPALIVE:
    case XS_SURVEY_TIMEOUT:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            int oval = 0;
            size_t olen = sizeof(oval);
    
            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            ret = xs_getsockopt(sock, optidx, &oval, &olen);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr, "getsockopt_int(%p, %ld, %p, %p) => %d (%ld, %ld)\n",
                    sock, (long) optidx, &oval, &olen, oval, (long) olen, (long) ret);
#endif

            if (ret >= 0) {
                jclass cval = 0;
                jfieldID ival = 0;
                
                ret = olen;
                
                cval = (*env)->GetObjectClass(env, optval);
                XS_ASSERT(cval);

                ival = (*env)->GetFieldID(env, cval, "value", "I");
                XS_ASSERT(ival);

                (*env)->SetIntField(env, optval, ival, oval);
            }
        } while (0);
        break;
    }
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "getsockopt_int, returning %ld\n", (long) ret);
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1long(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint optidx,
                                                                              jobject optval)
{
    int ret = -1;
    switch (optidx) {
    case XS_AFFINITY:
    case XS_MAXMSGSIZE:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            unsigned __int64 oval = 0;
            size_t olen = sizeof(oval);
    
            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            ret = xs_getsockopt(sock, optidx, &oval, &olen);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr, "getsockopt_long(%p, %d, %p, %p) => %lld (%d, %d)\n",
                    sock, optidx, &oval, &olen, oval, olen, ret);
#endif

            if (ret >= 0) {
                jclass cval = 0;
                jfieldID ival = 0;
                
                ret = olen;
                
                cval = (*env)->GetObjectClass(env, optval);
                XS_ASSERT(cval);

                ival = (*env)->GetFieldID(env, cval, "value", "J");
                XS_ASSERT(ival);

                (*env)->SetLongField(env, optval, ival, oval);
            }
        } while (0);
        break;
    }
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "getsockopt_long, returning %ld\n", (long) ret);
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1getsockopt_1buff(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint optidx,
                                                                              jobject optval)
{
    int ret = -1;
    switch (optidx) {
    case XS_IDENTITY:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            jbyte* oval = 0;
            size_t olen = 0;

            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            oval = (jbyte*) (*env)->GetDirectBufferAddress(env, optval);
            XS_ASSERT(oval);
            olen = (*env)->GetDirectBufferCapacity(env, optval);
            XS_ASSERT(olen >= 0);

            ret = xs_getsockopt(sock, optidx, oval, &olen);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr, "getsockopt_buff(%p, %d, %p, %p) => (%d, %d)\n",
                    sock, optidx, oval, &olen, olen, ret);
#endif

            if (ret >= 0) {
                ret = olen;
            }
        } while (0);
        break;
    }
    
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "getsockopt_buff, returning %ld\n", (long) ret);
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1int(JNIEnv* env,
                                                                             jobject obj,
                                                                             jint optidx,
                                                                             jint optval)
{
    int ret = -1;
    switch (optidx) {
    case XS_SNDHWM:
    case XS_RCVHWM:
    case XS_RATE:
    case XS_RECOVERY_IVL:
    case XS_SNDBUF:
    case XS_RCVBUF:
    case XS_LINGER:
    case XS_RECONNECT_IVL:
    case XS_RECONNECT_IVL_MAX:
    case XS_BACKLOG:
    case XS_MULTICAST_HOPS:
    case XS_RCVTIMEO:
    case XS_SNDTIMEO:
    case XS_IPV4ONLY:
    case XS_KEEPALIVE:
    case XS_SURVEY_TIMEOUT:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            int oval = 0;
            size_t olen = sizeof(oval);
                
            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            oval = optval;
        
            ret = xs_setsockopt(sock, optidx, &oval, olen);
            if (ret >= 0) {
                ret = olen;
            }
        } while (0);
        break;
    }

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "setsockopt_int, returning %ld\n", (long) ret);
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1long(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint optidx,
                                                                              jlong optval)
{
    int ret = -1;
    switch (optidx) {
    case XS_AFFINITY:
    case XS_MAXMSGSIZE:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            unsigned __int64 oval = 0;
            size_t olen = sizeof(oval);
                
            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            oval = optval;
        
            ret = xs_setsockopt(sock, optidx, &oval, olen);
            if (ret >= 0) {
                ret = olen;
            }
        } while (0);
        break;
    }

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "setsockopt_long, returning %ld\n", (long) ret);
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsOption_call_1setsockopt_1buff(JNIEnv* env,
                                                                              jobject obj,
                                                                              jint optidx,
                                                                              jobject optval)
{
    int ret = -1;
    switch (optidx) {
    case XS_SUBSCRIBE:
    case XS_UNSUBSCRIBE:
    case XS_IDENTITY:
        do {
            jclass cls = 0;
            jfieldID i_socket = 0;
            long f_socket = 0;
            void* sock = 0;
            jbyte* oval = 0;
            size_t olen = 0;
            jclass cval = 0;
            jmethodID mlim;

            // The class for the calling object
            cls = (*env)->GetObjectClass(env, obj);
            XS_ASSERT(cls);

            // Field "socket"
            i_socket = (*env)->GetFieldID(env, cls, "socket", "J");
            XS_ASSERT(i_socket);
            f_socket = (long) (*env)->GetLongField(env, obj, i_socket);
            sock = (void*) f_socket;

            oval = (jbyte*) (*env)->GetDirectBufferAddress(env, optval);
            XS_ASSERT(oval);

            olen = (*env)->GetDirectBufferCapacity(env, optval);
            XS_ASSERT(olen >= 0);

            cval = (*env)->GetObjectClass(env, optval);
            XS_ASSERT(cval);

            mlim = (*env)->GetMethodID(env, cval, "limit", "()I");
            XS_ASSERT(mlim);

            olen = (*env)->CallObjectMethod(env, optval, mlim);
            XS_ASSERT(olen >= 0);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr, "Got limit for buffer %p: %ld\n",
                    oval, (long) olen);
#endif
            
            ret = xs_setsockopt(sock, optidx, oval, olen);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr, "setsockopt_buff(%p, %d, %p, %ld) => (%d)\n",
                    sock, optidx, oval, (long) olen, ret);
#endif

            if (ret >= 0) {
                ret = olen;
            }
        } while (0);
        break;
    }

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "setsockopt_buff, returning %ld\n", (long) ret);
#endif

    return ret;
}
