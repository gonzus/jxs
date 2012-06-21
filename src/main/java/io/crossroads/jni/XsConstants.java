package io.crossroads.jni;

public class XsConstants {

    public XsConstants()
    {
        loadConstants();
    }
    
    native public int makeVersion(int major,
                                  int minor,
                                  int patch);

    /* Version constants */
    static public int XS_VERSION_MAJOR = -1;
    static public int XS_VERSION_MINOR = -1;
    static public int XS_VERSION_PATCH = -1;
    static public int XS_VERSION = -1;

    /* Socket types */
    static public int XS_PAIR = -1;
    static public int XS_PUB = -1;
    static public int XS_SUB = -1;
    static public int XS_REQ = -1;
    static public int XS_REP = -1;
    static public int XS_XREQ = -1;
    static public int XS_XREP = -1;
    static public int XS_PULL = -1;
    static public int XS_PUSH = -1;
    static public int XS_XPUB = -1;
    static public int XS_XSUB = -1;
    static public int XS_SURVEYOR = -1;
    static public int XS_RESPONDENT = -1;
    static public int XS_XSURVEYOR = -1;
    static public int XS_XRESPONDENT = -1;
    static public int XS_ROUTER = -1;
    static public int XS_DEALER = -1;
    
    /* Options for context definition */
    static public int XS_MAX_SOCKETS = -1;
    static public int XS_IO_THREADS = -1;
    static public int XS_PLUGIN = -1;

    /* Socket options */
    static public int XS_AFFINITY = -1;
    static public int XS_IDENTITY = -1;
    static public int XS_SUBSCRIBE = -1;
    static public int XS_UNSUBSCRIBE = -1;
    static public int XS_RATE = -1;
    static public int XS_RECOVERY_IVL = -1;
    static public int XS_SNDBUF = -1;
    static public int XS_RCVBUF = -1;
    static public int XS_RCVMORE = -1;
    static public int XS_FD = -1;
    static public int XS_EVENTS = -1;
    static public int XS_TYPE = -1;
    static public int XS_LINGER = -1;
    static public int XS_RECONNECT_IVL = -1;
    static public int XS_BACKLOG = -1;
    static public int XS_RECONNECT_IVL_MAX = -1;
    static public int XS_MAXMSGSIZE = -1;
    static public int XS_SNDHWM = -1;
    static public int XS_RCVHWM = -1;
    static public int XS_MULTICAST_HOPS = -1;
    static public int XS_RCVTIMEO = -1;
    static public int XS_SNDTIMEO = -1;
    static public int XS_IPV4ONLY = -1;
    static public int XS_KEEPALIVE = -1;
    static public int XS_SURVEY_TIMEOUT = -1;

    /* Message options */
    static public int XS_MORE = -1;
    
    /* Options for send / recv */
    static public int XS_DONTWAIT = -1;
    static public int XS_SNDMORE = -1;
    
    /* Options for I/O multiplexing */
    static public int XS_POLLIN = -1;
    static public int XS_POLLOUT = -1;
    static public int XS_POLLERR = -1;


    native private int loadConstants();
}
