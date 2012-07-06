#include "XsUtil.h"

#include "io_crossroads_jni_XsConstants.h"

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsErrors_loadErrors(JNIEnv* env,
                                                                  jobject obj)
{
    static struct {
        const char* name;
        int value;
    } E[] = {
        { "ENOMEM", ENOMEM },
        { "EFAULT", EFAULT },
        { "EINVAL", EINVAL },
        { "EMFILE", EMFILE },
        { "EINTR", EINTR },
        { "ENAMETOOLONG", ENAMETOOLONG },
        { "ENODEV", ENODEV },
        { "EAGAIN", EAGAIN },
        { "ETIMEDOUT", ETIMEDOUT },
        // 
        { "ENOTSUP", ENOTSUP },
        { "EPROTONOSUPPORT", EPROTONOSUPPORT },
        { "ENOBUFS", ENOBUFS },
        { "ENETDOWN", ENETDOWN },
        { "EADDRINUSE", EADDRINUSE },
        { "EADDRNOTAVAIL", EADDRNOTAVAIL },
        { "ECONNREFUSED", ECONNREFUSED },
        { "EINPROGRESS", EINPROGRESS },
        { "ENOTSOCK", ENOTSOCK },
        { "EAFNOSUPPORT", EAFNOSUPPORT },
        { "EFSM", EFSM },
        { "ENOCOMPATPROTO", ENOCOMPATPROTO },
        { "ETERM", ETERM },
        { 0, 0 },
    };
    jclass cls;
    jfieldID fid;
    int i;
    int n = 0;
 
    cls = (*env)->GetObjectClass(env, obj);
    XS_ASSERT(cls);
    
    for (i = 0; E[i].name != 0; ++i) {
        fid = (*env)->GetStaticFieldID(env, cls, E[i].name, "I");
        XS_ASSERT(fid);

        (*env)->SetStaticIntField(env, cls, fid, E[i].value);
        ++n;
    }
    
    return n;
}
