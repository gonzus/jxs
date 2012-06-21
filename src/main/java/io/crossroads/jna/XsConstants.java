package io.crossroads.jna;

public class XsConstants {
    /* Socket types */
    static public final int XS_PAIR = 0;
    static public final int XS_PUB = 1;
    static public final int XS_SUB = 2;
    static public final int XS_REQ = 3;
    static public final int XS_REP = 4;
    static public final int XS_XREQ = 5;
    static public final int XS_XREP = 6;
    static public final int XS_PULL = 7;
    static public final int XS_PUSH = 8;
    static public final int XS_XPUB = 9;
    static public final int XS_XSUB = 10;
    static public final int XS_SURVEYOR = 11;
    static public final int XS_RESPONDENT = 12;
    static public final int XS_XSURVEYOR = 13;
    static public final int XS_XRESPONDENT = 14;
    static public final int XS_ROUTER = XS_XREP;
    static public final int XS_DEALER = XS_XREQ;
    
    /* Options for context definition */
    static public final int XS_MAX_SOCKETS = 1;
    static public final int XS_IO_THREADS = 2;
    static public final int XS_PLUGIN = 3;

    /* Socket options */
    static public final int XS_AFFINITY = 4;
    static public final int XS_IDENTITY = 5;
    static public final int XS_SUBSCRIBE = 6;
    static public final int XS_UNSUBSCRIBE = 7;
    static public final int XS_RATE = 8;
    static public final int XS_RECOVERY_IVL = 9;
    static public final int XS_SNDBUF = 11;
    static public final int XS_RCVBUF = 12;
    static public final int XS_RCVMORE = 13;
    static public final int XS_FD = 14;
    static public final int XS_EVENTS = 15;
    static public final int XS_TYPE = 16;
    static public final int XS_LINGER = 17;
    static public final int XS_RECONNECT_IVL = 18;
    static public final int XS_BACKLOG = 19;
    static public final int XS_RECONNECT_IVL_MAX = 21;
    static public final int XS_MAXMSGSIZE = 22;
    static public final int XS_SNDHWM = 23;
    static public final int XS_RCVHWM = 24;
    static public final int XS_MULTICAST_HOPS = 25;
    static public final int XS_RCVTIMEO = 27;
    static public final int XS_SNDTIMEO = 28;
    static public final int XS_IPV4ONLY = 31;
    static public final int XS_KEEPALIVE = 32;
    static public final int XS_SURVEY_TIMEOUT = 35;

    /* Message options */
    static public final int XS_MORE = 1;

    /* Options for send / recv */
    static public final int XS_DONTWAIT = 1;
    static public final int XS_SNDMORE = 2;

    /* Options for I/O multiplexing */
    static public final short XS_POLLIN = 1;
    static public final short XS_POLLOUT = 2;
    static public final short XS_POLLERR = 4;
}
