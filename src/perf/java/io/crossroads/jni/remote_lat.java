package io.crossroads.jni;

import java.nio.ByteBuffer;

public class remote_lat {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: remote_lat <connect-to> <message-size> <roundtrip-count>\n");
            return;
        }
        
        XsLibrary xs = new XsLibrary();

        String connect_to;
        int roundtrip_count;
        int message_size;
        long ctx = 0;
        long sock = 0;
        int rc;
        int i;

        long watch = 0;
        long elapsed = 0;
        double latency = 0.0;

        connect_to = args[0];
        message_size = Integer.parseInt(args[1]);
        roundtrip_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          connect_to, message_size, roundtrip_count);

        ctx = xs.xs_init();
        if (ctx == 0) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        sock = xs.xs_socket(ctx, XsConstants.XS_REQ);
        if (sock == 0) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS REQ socket created\n");

        rc = xs.xs_connect(sock, connect_to);
        if (rc == -1) {
            System.out.printf("error in xs_connect: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS REQ socket connected to %s\n", connect_to);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(size);

        watch = xs.xs_stopwatch_start();

        System.out.printf("XS running %d iterations...\n", roundtrip_count);
        for (i = 0; i != roundtrip_count; i++) {
            rc = xs.xs_send(sock, bb, 0, message_size, 0);
            if (rc < 0) {
                System.out.printf("error in xs_send: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            if (rc != message_size) {
                System.out.printf("message of incorrect size sent\n");
                return;
            }

            rc = xs.xs_recv(sock, bb, 0, size, 0);
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

        elapsed = xs.xs_stopwatch_stop(watch);

        latency = (double) elapsed / (roundtrip_count * 2);
        
        System.out.printf("message size: %d [B]\n", message_size);
        System.out.printf("roundtrip count: %d\n", roundtrip_count);
        System.out.printf("average latency: %.3f [us]\n", latency);

        rc = xs.xs_close(sock);
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
