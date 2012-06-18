#include <xs/xs.h>
#include "io_crossroads_jni_XsPoller.h"

#define XS_DEBUG 0

static void release(JNIEnv* env,
                    jobject obj,
                    jfieldID i_poll_mem,
                    xs_pollitem_t* f_poll_mem);


JNIEXPORT jint JNICALL Java_io_crossroads_jni_XsPoller_call_1poll(JNIEnv* env,
                                                                  jobject obj,
                                                                  jint timeout)
{
    int j = 0;
    int ret = 0;

    // The class for the calling object
    jclass cls = (*env)->GetObjectClass(env, obj);

    // Field "poll_rdy"
    jfieldID i_poll_rdy = (*env)->GetFieldID(env, cls, "poll_rdy", "Z");
    int f_poll_rdy = (int) (*env)->GetBooleanField(env, obj, i_poll_rdy);

    // Field "poll_mem"
    jfieldID i_poll_mem = (*env)->GetFieldID(env, cls, "poll_mem", "J");
    xs_pollitem_t* f_poll_mem = (xs_pollitem_t*) (*env)->GetLongField(env, obj, i_poll_mem);

    // Field "next"
    jfieldID i_next = (*env)->GetFieldID(env, cls, "next", "I");
    int f_next = (int) (*env)->GetIntField(env, obj, i_next);

    if (! f_poll_rdy) {
        // There have been changes; must reflect Java socket info into
        // xs_pollitem_t array

        // Field "poll_siz"
        jfieldID i_poll_siz = (*env)->GetFieldID(env, cls, "poll_siz", "I");
        int f_poll_siz = (int) (*env)->GetIntField(env, obj, i_poll_siz);

        // Field "size"
        jfieldID i_size = (*env)->GetFieldID(env, cls, "size", "I");
        int f_size = (int) (*env)->GetIntField(env, obj, i_size);

        if (f_poll_siz < f_size) {
            // xs_pollitem_t array is too small, must enlarge it
            int nsiz = f_size;
            xs_pollitem_t* nmem = 0;
            // short* nidx =  0;
            
#if 0
            // Field "poll_idx"
            jfieldID i_poll_idx = (*env)->GetFieldID(env, cls, "poll_idx", "J");
            short* f_poll_idx = (short*) (*env)->GetLongField(env, obj, i_poll_idx);
#endif
            
#if defined(XS_DEBUG) && (XS_DEBUG > 0)
            fprintf(stderr,
                    "xs_poll: enlarging arrays from %d to %d\n",
                    f_poll_siz, nsiz);
#endif
            
            nmem = calloc(nsiz, sizeof(xs_pollitem_t));
            // nidx = calloc(nsiz, sizeof(short));

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
            // Field "sock", an array
            jfieldID i_sock = (*env)->GetFieldID(env, cls, "sock", "[J");
            jlongArray f_sock = (jlongArray) (*env)->GetObjectField(env, obj, i_sock);
            jsize l_sock = (*env)->GetArrayLength(env, f_sock);
            jlong* a_sock = (*env)->GetLongArrayElements(env, f_sock, 0);
            
            // Field "einp", an array
            jfieldID i_einp = (*env)->GetFieldID(env, cls, "einp", "[S");
            jshortArray f_einp = (jshortArray) (*env)->GetObjectField(env, obj, i_einp);
            jsize l_einp = (*env)->GetArrayLength(env, f_einp);
            jshort* a_einp = (*env)->GetShortArrayElements(env, f_einp, 0);
            
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
        // Will hold field "eout", an array
        jfieldID i_eout = (*env)->GetFieldID(env, cls, "eout", "[S");
        jshortArray f_eout = (jshortArray) (*env)->GetObjectField(env, obj, i_eout);
        jshort* a_eout = (*env)->GetShortArrayElements(env, f_eout, 0);

        jsize l_eout = (*env)->GetArrayLength(env, f_eout);

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
    // The class for the calling object
    jclass cls = (*env)->GetObjectClass(env, obj);

    // Field "poll_mem"
    jfieldID i_poll_mem = (*env)->GetFieldID(env, cls, "poll_mem", "J");
    xs_pollitem_t* f_poll_mem = (xs_pollitem_t*) (*env)->GetLongField(env, obj, i_poll_mem);

    release(env, obj, i_poll_mem, f_poll_mem);
}

static void release(JNIEnv* env,
                    jobject obj,
                    jfieldID i_poll_mem,
                    xs_pollitem_t* f_poll_mem)
{
    if (f_poll_mem == 0)
        return;

#if defined(XS_DEBUG) && (XS_DEBUG > 0)
    fprintf(stderr, "xs_poll: releasing poll memory\n");
#endif
    
    free(f_poll_mem);
    (*env)->SetLongField(env, obj, i_poll_mem, 0);
}
