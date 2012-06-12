package io.crossroads.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Tester {
    public static void main(String [] args) {
        Tester tester = new Tester();
        tester.allocate();
        tester.testLibrary();
        tester.testVersion();
        tester.testPoll();
        tester.dispose();
    }

    public Tester() {
    }
    
    public void allocate() {
        System.out.println("Starting Tester");
        xs = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);
        ctx = xs.xs_init();
        sock = xs.xs_socket(ctx, xs.XS_REQ);
    }

    public void dispose() {
        xs.xs_close(sock);
        xs.xs_term(ctx);
        xs = null;
        System.out.println("Finished Tester");
    }

    private boolean testLibrary() {
        if (xs == null) {
            System.out.println("xsLibrary is null");
            return false;
        }
        
        return true;
    }
    
    private void testVersion() {
        int[] major = new int[1];
        int[] minor = new int[1];
        int[] patch = new int[1];
        xs.xs_version(major, minor, patch);
        System.out.printf("XS version is %d.%d.%d\n",
                          major[0], minor[0], patch[0]);
    }

    private void testPoll() {
        XsPollItem[] items = new XsPollItem[1];
        items[0] = new XsPollItem(sock, null, xs.XS_POLLIN);
        int n = xs.xs_poll(items, 1, 0);
        System.out.printf("XS polled socket, got %d events\n",
                          n);
    }

    private XsLibrary xs = null;
    private Pointer ctx = null;
    private Pointer sock = null;
}
