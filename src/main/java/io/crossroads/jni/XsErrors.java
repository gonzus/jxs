package io.crossroads.jni;

public class XsErrors {

    public XsErrors()
    {
        loadErrors();
    }
    
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
