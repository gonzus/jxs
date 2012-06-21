package io.crossroads.jni;

public class XsErrors {

    public XsErrors()
    {
        loadErrors();
    }
    
    static public int ENOMEM = -1;
    static public int EFAULT = -1;
    static public int EINVAL = -1;
    static public int EMFILE = -1;
    static public int EINTR = -1;
    static public int ENAMETOOLONG = -1;
    static public int ENODEV = -1;
    static public int EAGAIN = -1;
    static public int ETIMEDOUT = -1;

    static public int ENOTSUP = -1;
    static public int EPROTONOSUPPORT = -1;
    static public int ENOBUFS = -1;
    static public int ENETDOWN = -1;
    static public int EADDRINUSE = -1;
    static public int EADDRNOTAVAIL = -1;
    static public int ECONNREFUSED = -1;
    static public int EINPROGRESS = -1;
    static public int ENOTSOCK = -1;
    static public int EAFNOSUPPORT = -1;

    static public int EFSM = -1;
    static public int ENOCOMPATPROTO = -1;
    static public int ETERM = -1;
    static public int EMTHREAD = -1;

    native private int loadErrors();
}
