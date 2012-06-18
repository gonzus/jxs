package io.crossroads.jni;

public class XsPoller {

    public XsPoller()
    {
        poll_mem = 0;
        reset();
    }

    public void reset()
    {
        call_release();

        size = 0;
        next = 0;
        poll_rdy = false;
        poll_mem = 0;
        poll_idx = 0;
        poll_siz = 0;
    }

    public int addSocket(long sock,
                         int einp)
    {
        check_space();
        poll_rdy = false;
        this.sock[next] = sock;
        this.einp[next] = (short) einp;
        this.eout[next] = 0;
        return next++;
    }

    public int poll(int timeout)
    {
        int j;
        System.out.printf("XS will call poll: [%d:%x:%x:%d] [%d:%d]\n",
                          (poll_rdy ? 1 : 0), poll_mem, poll_idx, poll_siz,
                          size, next);
        for (j = 0; j < next; ++j) {
            System.out.printf("XS   sock[%2d] = %8x | einp[%2d] = %4d | eout[%2d] = %4d\n",
                              j, sock[j], j, einp[j], j, eout[j]);
        }
        int ret = 0;
        ret = call_poll(timeout);
        System.out.printf("XS called poll: [%d:%x:%x:%d] [%d:%d] => %d\n",
                          (poll_rdy ? 1 : 0), poll_mem, poll_idx, poll_siz,
                          size, next, ret);
        for (j = 0; j < next; ++j) {
            System.out.printf("XS   sock[%2d] = %8x | einp[%2d] = %4d | eout[%2d] = %4d\n",
                              j, sock[j], j, einp[j], j, eout[j]);
        }
        return ret;
    }

    protected void finalize()
        throws Throwable {
        try {
            reset();
        } finally {
            super.finalize();
        }
    }
    
    private void check_space()
    {
        if (next < size)
            return;

        int nz = size+10;
        System.out.printf("XS will enlarge array from %d to %d\n",
                          size, nz);
        long[] ns = new long[nz];
        short[] ni = new short[nz];
        short[] no = new short[nz];
        for (int i = 0; i < size; ++i) {
            ns[i] = sock[i];
            ni[i] = einp[i];
            no[i] = eout[i];
        }
        
        size = nz;
        sock = ns;
        einp = ni;
        eout = no;
    }
    
    private native int call_poll(int timeout);
    private native int call_release();

    private int size;
    private int next;
    private long[] sock;
    private short[] einp;
    private short[] eout;

    // Data handled with JNI
    private boolean poll_rdy;
    private long poll_mem;
    private long poll_idx;
    private int poll_siz;
}
