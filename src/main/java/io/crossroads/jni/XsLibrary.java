package io.crossroads.jni;

import java.nio.ByteBuffer;

public class XsLibrary {
    static {
        System.loadLibrary ("jxs");
    }
    
    public native int xs_version(Integer major,
                                 Integer minor,
                                 Integer patch);

    public native int xs_errno();
    public native String xs_strerror(int errnum);

    public native long xs_init();
    public native int xs_term(long context);

    static final int XS_PAIR = 0;
    static final int XS_PUB = 1;
    static final int XS_SUB = 2;
    static final int XS_REQ = 3;
    static final int XS_REP = 4;
    static final int XS_XREQ = 5;
    static final int XS_XREP = 6;
    static final int XS_PULL = 7;
    static final int XS_PUSH = 8;
    static final int XS_XPUB = 9;
    static final int XS_XSUB = 10;
    static final int XS_SURVEYOR = 11;
    static final int XS_RESPONDENT = 12;
    static final int XS_XSURVEYOR = 13;
    static final int XS_XRESPONDENT = 14;
    
    static final int XS_ROUTER = XS_XREP;
    static final int XS_DEALER = XS_XREQ;

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

    public native long xs_stopwatch_start();
    public native long xs_stopwatch_stop(long watch);
}
