#include "XsUtil.h"

#include "io_crossroads_jni_XsConstants.h"

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsConstants_makeVersion(JNIEnv* env,
                                                                      jobject obj,
                                                                      jint major,
                                                                      jint minor,
                                                                      jint patch)
{
    return XS_MAKE_VERSION(major, minor, patch);
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsConstants_loadConstants(JNIEnv* env,
                                                                        jobject obj)
{
    static struct {
        const char* name;
        int value;
    } K[] = {
        { "XS_VERSION_MAJOR", XS_VERSION_MAJOR },
        { "XS_VERSION_MINOR", XS_VERSION_MINOR },
        { "XS_VERSION_PATCH", XS_VERSION_PATCH },
        { "XS_VERSION", XS_VERSION },
        { "XS_PAIR", XS_PAIR },
        { "XS_PUB", XS_PUB },
        { "XS_SUB", XS_SUB },
        { "XS_REQ", XS_REQ },
        { "XS_REP", XS_REP },
        { "XS_XREQ", XS_XREQ },
        { "XS_XREP", XS_XREP },
        { "XS_PULL", XS_PULL },
        { "XS_PUSH", XS_PUSH },
        { "XS_XPUB", XS_XPUB },
        { "XS_XSUB", XS_XSUB },
        { "XS_SURVEYOR", XS_SURVEYOR },
        { "XS_RESPONDENT", XS_RESPONDENT },
        { "XS_XSURVEYOR", XS_XSURVEYOR },
        { "XS_XRESPONDENT", XS_XRESPONDENT },
        { "XS_ROUTER", XS_ROUTER },
        { "XS_DEALER", XS_DEALER },
        { "XS_MAX_SOCKETS", XS_MAX_SOCKETS },
        { "XS_IO_THREADS", XS_IO_THREADS },
        { "XS_PLUGIN", XS_PLUGIN },
        { "XS_AFFINITY", XS_AFFINITY },
        { "XS_IDENTITY", XS_IDENTITY },
        { "XS_SUBSCRIBE", XS_SUBSCRIBE },
        { "XS_UNSUBSCRIBE", XS_UNSUBSCRIBE },
        { "XS_RATE", XS_RATE },
        { "XS_RECOVERY_IVL", XS_RECOVERY_IVL },
        { "XS_SNDBUF", XS_SNDBUF },
        { "XS_RCVBUF", XS_RCVBUF },
        { "XS_RCVMORE", XS_RCVMORE },
        { "XS_FD", XS_FD },
        { "XS_EVENTS", XS_EVENTS },
        { "XS_TYPE", XS_TYPE },
        { "XS_LINGER", XS_LINGER },
        { "XS_RECONNECT_IVL", XS_RECONNECT_IVL },
        { "XS_BACKLOG", XS_BACKLOG },
        { "XS_RECONNECT_IVL_MAX", XS_RECONNECT_IVL_MAX },
        { "XS_MAXMSGSIZE", XS_MAXMSGSIZE },
        { "XS_SNDHWM", XS_SNDHWM },
        { "XS_RCVHWM", XS_RCVHWM },
        { "XS_MULTICAST_HOPS", XS_MULTICAST_HOPS },
        { "XS_RCVTIMEO", XS_RCVTIMEO },
        { "XS_SNDTIMEO", XS_SNDTIMEO },
        { "XS_IPV4ONLY", XS_IPV4ONLY },
        { "XS_KEEPALIVE", XS_KEEPALIVE },
        { "XS_PATTERN_VERSION", XS_PATTERN_VERSION },
        { "XS_SURVEY_TIMEOUT", XS_SURVEY_TIMEOUT },
        { "XS_SERVICE_ID", XS_SERVICE_ID },
        { "XS_MORE", XS_MORE },
        { "XS_DONTWAIT", XS_DONTWAIT },
        { "XS_SNDMORE", XS_SNDMORE },
        { "XS_POLLIN", XS_POLLIN },
        { "XS_POLLOUT", XS_POLLOUT },
        { "XS_POLLERR", XS_POLLERR },
        { 0, 0 },
    };
    jclass cls;
    jfieldID fid;
    int i;
    int n = 0;
 
    cls = (*env)->GetObjectClass(env, obj);
    XS_ASSERT(cls);
    
    for (i = 0; K[i].name != 0; ++i) {
        fid = (*env)->GetStaticFieldID(env, cls, K[i].name, "I");
        XS_ASSERT(fid);

        (*env)->SetStaticIntField(env, cls, fid, K[i].value);
        ++n;
    }
    
    return n;
}
