#include "XsUtil.h"

#include "io_crossroads_jni_XsPoller.h"

static int release(JNIEnv* env,
                   jobject obj,
                   jfieldID i_poll_mem,
                   xs_pollitem_t* f_poll_mem);


JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsPoller_call_1poll(JNIEnv* env,
                                                                  jobject obj,
                                                                  jint timeout)
{
    int j = 0;
    int ret = 0;
    int alloc = 0;

    jclass cls = 0;
    jfieldID i_poll_rdy = 0;
    int f_poll_rdy = 0;
    jfieldID i_poll_mem = 0;
    xs_pollitem_t* f_poll_mem = 0;
    jfieldID i_next = 0;
    int f_next = 0;
    jfieldID i_size = 0;
    int f_size = 0;
    
    // The class for the calling object
    cls = (*env)->GetObjectClass(env, obj);
    XS_ASSERT(cls);

    // Field "poll_rdy"
    i_poll_rdy = (*env)->GetFieldID(env, cls, "poll_rdy", "Z");
    XS_ASSERT(i_poll_rdy);
    f_poll_rdy = (int) (*env)->GetBooleanField(env, obj, i_poll_rdy);

    // Field "poll_mem"
    i_poll_mem = (*env)->GetFieldID(env, cls, "poll_mem", "J");
    XS_ASSERT(i_poll_mem);
    f_poll_mem = (xs_pollitem_t*) (*env)->GetLongField(env, obj, i_poll_mem);

    // Field "next"
    i_next = (*env)->GetFieldID(env, cls, "next", "I");
    XS_ASSERT(i_next);
    f_next = (int) (*env)->GetIntField(env, obj, i_next);

    // Field "size"
    i_size = (*env)->GetFieldID(env, cls, "size", "I");
    XS_ASSERT(i_size);
    f_size = (int) (*env)->GetIntField(env, obj, i_size);

    if (! f_poll_rdy) {
        // There have been changes; must reflect Java socket info into
        // xs_pollitem_t array

        jfieldID i_poll_siz = 0;
        int f_poll_siz = 0;

        // Field "poll_siz"
        i_poll_siz = (*env)->GetFieldID(env, cls, "poll_siz", "I");
        XS_ASSERT(i_poll_siz);
        f_poll_siz = (int) (*env)->GetIntField(env, obj, i_poll_siz);

        if (f_poll_siz < f_size) {
            // xs_pollitem_t array is too small, must enlarge it

            int nsiz = 0;
            xs_pollitem_t* nmem = 0;
#if 0
            short* nidx =  0;
            jfieldID i_poll_idx = 0;
            short* f_poll_idx = 0;
#endif
            
            nsiz = f_size;
            
#if 0
            // Field "poll_idx"
            i_poll_idx = (*env)->GetFieldID(env, cls, "poll_idx", "J");
            XS_ASSERT(i_poll_idx);
            f_poll_idx = (short*) (*env)->GetLongField(env, obj, i_poll_idx);
#endif
            
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: enlarging arrays from %d to %d\n",
                    f_poll_siz, nsiz);
#endif
            
            nmem = calloc(nsiz, sizeof(xs_pollitem_t));
            XS_ASSERT(nmem);

#if 0
            nidx = calloc(nsiz, sizeof(short));
#endif
            
            for (j = 0; j < f_poll_siz; ++j) {
                nmem[j] = f_poll_mem[j];
                // nidx[j] = f_poll_idx[j];
            }
            release(env, obj, i_poll_mem, f_poll_mem);
            f_poll_mem = nmem;
            // f_poll_idx = nidx;
            f_poll_siz = nsiz;

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: storing new C variables in Java\n");
#endif
            
            (*env)->SetLongField(env, obj, i_poll_mem, (jlong) f_poll_mem);
            // (*env)->SetLongField(env, obj, i_poll_idx, (jlong) f_poll_idx);
            (*env)->SetIntField(env, obj, i_poll_siz, (jint) f_poll_siz);
        }

        // Get values for sockets and events from Java
        do {
            jfieldID i_sock = 0;
            jlongArray f_sock = 0;
            jsize l_sock = 0;
            jlong* a_sock = 0;
            jfieldID i_einp = 0;
            jshortArray f_einp = 0;
            jsize l_einp = 0;
            jshort* a_einp = 0;

            // Field "sock", an array
            i_sock = (*env)->GetFieldID(env, cls, "sock", "[J");
            XS_ASSERT(i_sock);
            f_sock = (jlongArray) (*env)->GetObjectField(env, obj, i_sock);
            l_sock = (*env)->GetArrayLength(env, f_sock);
            XS_ASSERT(f_size == l_sock);
            a_sock = (*env)->GetLongArrayElements(env, f_sock, 0);
            XS_ASSERT(a_sock);
            
            // Field "einp", an array
            i_einp = (*env)->GetFieldID(env, cls, "einp", "[S");
            XS_ASSERT(i_einp);
            f_einp = (jshortArray) (*env)->GetObjectField(env, obj, i_einp);
            l_einp = (*env)->GetArrayLength(env, f_einp);
            XS_ASSERT(f_size == l_einp);
            a_einp = (*env)->GetShortArrayElements(env, f_einp, 0);
            XS_ASSERT(a_einp);
            
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: input arrays are %ld / %ld long\n",
                    (long) l_sock, (long) l_einp);
#endif

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: updating C view of sockets / events to poll\n");
#endif

            for (j = 0; j < f_next; ++j) {
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
                fprintf(stderr,
                        "xs_poll: sock[%2d] = %8lx | einp[%2d] = %4hd\n",
                        j, (long) a_sock[j], j, (long) a_einp[j]);
#endif
                f_poll_mem[j].socket  = (void*) a_sock[j];
                f_poll_mem[j].events  = (short) a_einp[j];
                f_poll_mem[j].revents = 0;
            }
            
            (*env)->ReleaseLongArrayElements(env, f_sock, a_sock, 0);
            (*env)->ReleaseShortArrayElements(env, f_einp, a_einp, 0);
        } while (0);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
        fprintf(stderr,
                "xs_poll: storing readiness in Java\n");
#endif
        f_poll_rdy = 1;
        (*env)->SetBooleanField(env, obj, i_poll_rdy, (jboolean) f_poll_rdy);
    }

    // Prepare for running poll by resetting all eout:
    for (j = 0; j < f_next; ++j) {
        f_poll_mem[j].revents = 0;
    }
    
    // Issue the poll call:
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_poll: polling %d elements\n",
            f_next);
#endif
    
    ret = xs_poll(f_poll_mem, f_next, timeout);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr,
            "xs_poll(%p, %d, %ld) => %d\n",
            f_poll_mem, f_next, (long) timeout, ret);
#endif
    
    // Reflect revents back into Java
    do {
        jfieldID i_eout = 0;
        jshortArray f_eout = 0;
        jshort* a_eout = 0;
        jsize l_eout = 0;

        // Will hold field "eout", an array
        i_eout = (*env)->GetFieldID(env, cls, "eout", "[S");
        XS_ASSERT(i_eout);
        f_eout = (jshortArray) (*env)->GetObjectField(env, obj, i_eout);
        a_eout = (*env)->GetShortArrayElements(env, f_eout, 0);
        XS_ASSERT(a_eout);
        l_eout = (*env)->GetArrayLength(env, f_eout);
        XS_ASSERT(f_size == l_eout);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
        fprintf(stderr,
                "xs_poll: output arrays are %ld long\n",
                (long) l_eout);
#endif

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
        fprintf(stderr, "xs_poll: storing eout in Java\n");
#endif
        for (j = 0; j < f_next; ++j) {
            // short int x = j+11;
            short int x = f_poll_mem[j].revents;
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: setting eout[%2d] to %hd\n",
                    j, (short) x);
#endif
            a_eout[j] = x;
        }

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
        fprintf(stderr,
                "xs_poll: releasing modified array\n");
#endif
        (*env)->ReleaseShortArrayElements(env, f_eout, a_eout, 0);
    } while (0);

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "xs_poll: done in C\n");
    fprintf(stderr, "------------------\n");
#endif

    return ret;
}

JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsPoller_call_1release(JNIEnv* env,
                                                                     jobject obj)
{
    jclass cls = 0;
    jfieldID i_poll_mem = 0;
    xs_pollitem_t* f_poll_mem = 0;

    // The class for the calling object
    cls = (*env)->GetObjectClass(env, obj);
    XS_ASSERT(cls);

    // Field "poll_mem"
    i_poll_mem = (*env)->GetFieldID(env, cls, "poll_mem", "J");
    XS_ASSERT(i_poll_mem);
    f_poll_mem = (xs_pollitem_t*) (*env)->GetLongField(env, obj, i_poll_mem);

    release(env, obj, i_poll_mem, f_poll_mem);
}

static int release(JNIEnv* env,
                   jobject obj,
                   jfieldID i_poll_mem,
                   xs_pollitem_t* f_poll_mem)
{
    if (f_poll_mem == 0)
        return 0;

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "xs_poll: releasing poll memory\n");
#endif
    
    free(f_poll_mem);
    (*env)->SetLongField(env, obj, i_poll_mem, 0);
    return 1;
}
