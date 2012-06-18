package io.crossroads.jni;

import java.nio.ByteBuffer;

public class local_lat {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: local_lat <bind-to> <message-size> <roundtrip-count>\n");
            return;
        }
        
        XsLibrary xs = new XsLibrary();

        String bind_to;
        int roundtrip_count;
        int message_size;
        long ctx = 0;
        long s = 0;
        int rc;
        int i;

        bind_to = args[0];
        message_size = Integer.parseInt(args[1]);
        roundtrip_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          bind_to, message_size, roundtrip_count);

        ctx = xs.xs_init();
        if (ctx == 0) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        s = xs.xs_socket(ctx, XsConstants.XS_REP);
        if (s == 0) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS REP socket created\n");

        rc = xs.xs_bind(s, bind_to);
        if (rc == -1) {
            System.out.printf("error in xs_bind: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS REP socket bound to %s\n", bind_to);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(size);

        System.out.printf("XS running %d iterations...\n", roundtrip_count);
        for (i = 0; i != roundtrip_count; i++) {
            rc = xs.xs_recv(s, bb, 0, size, 0);
            if (rc < 0) {
                System.out.printf("error in xs_recv: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            if (rc != message_size) {
                System.out.printf("message of incorrect size received\n");
                return;
            }

            rc = xs.xs_send(s, bb, 0, message_size, 0);
            if (rc < 0) {
                System.out.printf("error in xs_send: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            if (rc != message_size) {
                System.out.printf("message of incorrect size sent\n");
                return;
            }
        }

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
