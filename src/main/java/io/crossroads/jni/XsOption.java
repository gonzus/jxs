package io.crossroads.jni;

import java.nio.ByteBuffer;

public class XsOption {

    public XsOption()
    {
        context = 0;
        socket = 0;
    }

    public void setContext(long context)
    {
        this.context = context;
    }

    public void setSocket(long socket)
    {
        this.socket = socket;
    }

    public int getSockoptInt(int optidx,
                             Integer optval)
    {
        // System.out.println("calling getSockOptInt(" + optidx + ")");
        int ret = call_getsockopt_int(optidx, optval);
        // System.out.println("called getSockOptInt(" + optidx + ") => " + optval + " (" + ret + ")");
        return ret;
    }

    public int getSockoptLong(int optidx,
                              Long optval)
    {
        // System.out.println("calling getSockOptLong(" + optidx + ")");
        int ret = call_getsockopt_long(optidx, optval);
        // System.out.println("called getSockOptLong(" + optidx + ") => " + optval + " (" + ret + ")");
        return ret;
    }

    public int getSockoptBuffer(int optidx,
                                ByteBuffer optval)
    {
        optval.clear();
        // System.out.println("calling getSockOptBuffer(" + optidx + ")");
        int ret = call_getsockopt_buff(optidx, optval);
        if (ret >= 0) {
            optval.position(ret);
            optval.flip();
        }
        // System.out.println("called getSockOptBuffer(" + optidx + ") => [" + optval + "] (" + ret + ")");
        return ret;
    }

    public int setSockoptInt(int optidx,
                             int optval)
    {
        // System.out.println("calling setSockOptInt(" + optidx + ") to " + optval);
        int ret = call_setsockopt_int(optidx, optval);
        // System.out.println("called setSockOptInt(" + optidx + ") => " + ret);
        return ret;
    }

    public int setSockoptLong(int optidx,
                              long optval)
    {
        // System.out.println("calling setSockOptLong(" + optidx + ") to " + optval);
        int ret = call_setsockopt_long(optidx, optval);
        // System.out.println("called setSockOptLong(" + optidx + ") => " + ret);
        return ret;
    }

    public int setSockoptBuffer(int optidx,
                                ByteBuffer optval)
    {
        // System.out.println("calling setSockOptBuffer(" + optidx + ") to [" + optval + "]");
        int ret = call_setsockopt_buff(optidx, optval);
        if (ret >= 0) {
            optval.clear();
        }
        // System.out.println("called setSockOptBuffer(" + optidx + ") => " + ret);
        return ret;
    }

    private native int call_getsockopt_int(int optidx,
                                           Integer optval);
    private native int call_getsockopt_long(int optidx,
                                            Long optval);
    private native int call_getsockopt_buff(int optidx,
                                            ByteBuffer optval);

    private native int call_setsockopt_int(int optidx,
                                           int optval);
    private native int call_setsockopt_long(int optidx,
                                            long optval);
    private native int call_setsockopt_buff(int optidx,
                                            ByteBuffer optval);
    
    private long context;
    private long socket;
}
