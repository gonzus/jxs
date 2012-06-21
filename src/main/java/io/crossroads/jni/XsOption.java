package io.crossroads.jni;

public class XsOption {

    public XsOption()
    {
        context = 0;
        socket = 0;

        opt_int = 0;
        opt_long = 0L;
        opt_binary = null;
    }

    public void setContext(long context)
    {
        this.context = context;
    }

    public void setSocket(long socket)
    {
        this.socket = socket;
    }

    public void setSockoptInt(int option,
                              int value)
    {
        call_setsockopt_int(option, value);
    }

    public int getSockoptInt(int option)
    {
        int ret = call_getsockopt_int(option);
        return opt_int;
    }

    public void setSockoptLong(int option,
                              long value)
    {
        call_setsockopt_long(option, value);
    }

    public long getSockoptLong(int option)
    {
        int ret = call_getsockopt_long(option);
        return opt_long;
    }

    public void setSockoptBinary(int option,
                                 Byte[] value)
    {
        call_setsockopt_binary(option, value);
    }

    public Byte[] getSockoptBinary(int option)
    {
        int ret = call_getsockopt_binary(option);
        return opt_binary;
    }


    private native void call_setsockopt_int(int option,
                                            int value);
    private native void call_setsockopt_long(int option,
                                             long value);
    private native void call_setsockopt_binary(int option,
                                               Byte[] value);
    private native int call_getsockopt_int(int option);
    private native int call_getsockopt_long(int option);
    private native int call_getsockopt_binary(int option);

    private long context;
    private long socket;

    Integer opt_int;
    Long opt_long;
    Byte[] opt_binary;
}
