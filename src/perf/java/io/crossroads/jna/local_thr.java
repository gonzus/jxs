package io.crossroads.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.nio.ByteBuffer;

public class local_thr {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: local_thr <bind-to> <message-size> <message-count>\n");
            return;
        }
        
        XsLibrary xs = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);

        String bind_to;
        int message_count;
        int message_size;
        Pointer ctx = null;
        Pointer s = null;
        int rc;
        int i;

        Pointer watch = null;
        long elapsed = 0;
        long throughput = 0;
        double megabits = 0.0;

        bind_to = args[0];
        message_size = Integer.parseInt(args[1]);
        message_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          bind_to, message_size, message_count);

        ctx = xs.xs_init();
        if (ctx == null) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        s = xs.xs_socket(ctx, XsLibrary.XS_PULL);
        if (s == null) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PULL socket created\n");

        //  Add your socket options here.

        rc = xs.xs_bind(s, bind_to);
        if (rc == -1) {
            System.out.printf("error in xs_bind: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PULL socket bound to %s\n", bind_to);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocate(size);
        byte[] bba = bb.array();

        rc = xs.xs_recv(s, bba, size, 0);
        if (rc < 0) {
            System.out.printf("error in xs_recv: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        if (rc != message_size) {
            System.out.printf("message of incorrect size received\n");
            return;
        }

        watch = xs.xs_stopwatch_start();

        System.out.printf("XS running %d iterations...\n", message_count - 1);
        for (i = 0; i != message_count - 1; i++) {
            rc = xs.xs_recv(s, bba, size, 0);
            if (rc < 0) {
                System.out.printf("error in xs_recv: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            if (rc != message_size) {
                System.out.printf("message of incorrect size received\n");
                return;
            }
        }

        elapsed = xs.xs_stopwatch_stop(watch).longValue();
        if (elapsed == 0)
            elapsed = 1;

        throughput = (long) ((double) message_count / (double) elapsed * 1000000);
        megabits = (double) (throughput * message_size * 8) / 1000000;

        System.out.printf("message size: %d [B]\n", message_size);
        System.out.printf("message count: %d\n", message_count);
        System.out.printf("mean throughput: %d [msg/s]\n", (int) throughput);
        System.out.printf("mean throughput: %.3f [Mb/s]\n", megabits);

        rc = xs.xs_close(s);
        if (rc != 0) {
            System.out.printf("error in xs_close: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }

        rc = xs.xs_term(ctx);
        if (rc != 0) {
            System.out.printf("error in xs_term: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS done running\n");
    }
}
