package io.crossroads.jni;

import java.nio.ByteBuffer;

public class XsLibrary {
    static {
        ensureNativeCode();
    }

    public XsLibrary()
    {
        xc = new XsConstants();
        xe = new XsErrors();
    }
    
    public XsConstants xc;
    public XsErrors xe;
    
    public native int xs_version(Integer major,
                                 Integer minor,
                                 Integer patch);

    public native int xs_errno();
    public native String xs_strerror(int errnum);

    public native long xs_init();
    public native int xs_term(long context);
    
    public native long xs_socket(long context,
                                 int type);
    public native int xs_close(long socket);

    public native int xs_bind(long socket,
                              String address);
    public native int xs_connect (long socket,
                                  String address);
    public native int xs_shutdown(long socket,
                                  int how);
    public native int xs_send(long socket,
                              ByteBuffer buffer,
                              int offset,
                              int length,
                              int flags);
    public native int xs_recv(long socket,
                              ByteBuffer buffer,
                              int offset,
                              int length,
                              int flags);

    /*
     * TODO:
     
    public native int xs_setctxopt(long context,
                                   int option,
                                   const void* optval,
                                   int optvallen);

    public native int xs_setsockopt_int(long context,
                                        int option,
                                        int val);
    public native int xs_setsockopt_long(long context,
                                         int option,
                                         long val);
    public native int xs_setsockopt_binary(long context,
                                           int option,
                                           Byte[] val);
    
    public native int xs_getsockopt_int(long context,
                                        int option,
                                        int* val);

    public native int xs_getsockopt_long(long context,
                                         int option,
                                         long* val);
    
    public native int xs_getsockopt_binary(long context,
                                           int option,
                                           Byte[] val);
    */
    
    public native long xs_stopwatch_start();
    public native long xs_stopwatch_stop(long watch);


    private static void ensureNativeCode()
    {
        System.loadLibrary ("jxs");
    }
}
