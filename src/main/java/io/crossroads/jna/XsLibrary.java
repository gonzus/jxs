package io.crossroads.jna;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;

public interface XsLibrary extends Library {
    void xs_version(int[] major, int[] minor, int[] patch);

    static final int ENOTSUP = 1;
    static final int EPROTONOSUPPORT = 2;
    static final int ENOBUFS = 3;
    static final int ENETDOWN = 4;
    static final int EADDRINUSE = 5;
    static final int EADDRNOTAVAIL = 6;
    static final int ECONNREFUSED = 7;
    static final int EINPROGRESS = 8;
    static final int ENOTSOCK = 9;
    static final int EAFNOTSUPPORT = 10;

    static final int EFSM = 51;
    static final int ENOCOMPATPROTO = 52;
    static final int ETERM = 53;
    static final int EMTHREAD = 54;

    int xs_errno();

    String xs_strerror(int errnum);

    int xs_msg_init(XsMsg msg);

    int xs_msg_init_size(XsMsg msg,
                         NativeLong size);

    int xs_msg_init_data(XsMsg msg,
                         Pointer data,
                         NativeLong size,
                         XsFree ffn,
                         Pointer hint);

    int xs_msg_close(XsMsg msg);

    int xs_msg_move(XsMsg dest,
                    XsMsg src);

    int xs_msg_copy(XsMsg dest,
                    XsMsg src);

    Pointer xs_msg_data(XsMsg msg);

    NativeLong xs_msg_size(XsMsg msg);

    int xs_getmsgopt(XsMsg msg,
                     int option,
                     Pointer optval,
                     LongByReference optvallen);

    static final int XS_MAX_SOCKETS = 1;
    static final int XS_IO_THREADS = 2;
    static final int XS_PLUGIN = 3;

    Pointer xs_init();

    int xs_term(Pointer context);

    int xs_setctxopt(Pointer context,
                     int option,
                     Pointer optval,
                     NativeLong optvallen);

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
    
    static final int XS_AFFINITY = 4;
    static final int XS_IDENTITY = 5;
    static final int XS_SUBSCRIBE = 6;
    static final int XS_UNSUBSCRIBE = 7;
    static final int XS_RATE = 8;
    static final int XS_RECOVERY_IVL = 9;
    static final int XS_SNDBUF = 11;
    static final int XS_RCVBUF = 12;
    static final int XS_RCVMORE = 13;
    static final int XS_FD = 14;
    static final int XS_EVENTS = 15;
    static final int XS_TYPE = 16;
    static final int XS_LINGER = 17;
    static final int XS_RECONNECT_IVL = 18;
    static final int XS_BACKLOG = 19;
    static final int XS_RECONNECT_IVL_MAX = 21;
    static final int XS_MAXMSGSIZE = 22;
    static final int XS_SNDHWM = 23;
    static final int XS_RCVHWM = 24;
    static final int XS_MULTICAST_HOPS = 25;
    static final int XS_RCVTIMEO = 27;
    static final int XS_SNDTIMEO = 28;
    static final int XS_IPV4ONLY = 31;
    static final int XS_KEEPALIVE = 32;
    static final int XS_PATTERN_VERSION = 33;
    static final int XS_SURVEY_TIMEOUT = 35;
    static final int XS_SERVICE_ID = 36;

    static final int XS_MORE = 1;

    static final int XS_DONTWAIT = 1;
    static final int XS_SNDMORE = 2;

    Pointer xs_socket(Pointer context,
                      int type);

    int xs_close(Pointer socket);

    int xs_setsockopt(Pointer socket,
                      int option_name,
                      Pointer optval,
                      NativeLong optvallen);

    int xs_getsockopt(Pointer socket,
                      int option,
                      Pointer optval,
                      LongByReference optvallen);

    int xs_bind(Pointer socket,
                String address);

    int xs_connect(Pointer socket,
                   String address);

    int xs_shutdown(Pointer socket,
                    int how);
    
    int xs_send(Pointer socket,
                byte[] buf,
                int len,
                int flags);

    int xs_recv(Pointer socket,
                byte[] buf,
                int len,
                int flags);

    int xs_sendmsg(Pointer socket,
                   XsMsg message,
                   int flags);

    int xs_recvmsg(Pointer socket,
                   XsMsg message,
                   int flags);

    static final short XS_POLLIN = 1;
    static final short XS_POLLOUT = 2;
    static final short XS_POLLERR = 4;

    int xs_poll(XsPollItem items[],
                int nitems,
                int timeout);

    Pointer xs_stopwatch_start();

    NativeLong xs_stopwatch_stop(Pointer watch);

    // TODO:
    // plugins (does it make sense?)
}
