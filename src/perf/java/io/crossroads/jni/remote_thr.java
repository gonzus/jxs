package io.crossroads.jni;

import java.nio.ByteBuffer;

public class remote_thr {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: remote_thr <connect-to> <message-size> <message-count>\n");
            return;
        }
        
        XsLibrary xs = new XsLibrary();

        String connect_to;
        int message_count;
        int message_size;
        long ctx = 0;
        long sock = 0;
        int rc;
        int i;

        connect_to = args[0];
        message_size = Integer.parseInt(args[1]);
        message_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          connect_to, message_size, message_count);

        ctx = xs.xs_init();
        if (ctx == 0) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        sock = xs.xs_socket(ctx, XsConstants.XS_PUSH);
        if (sock == 0) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PUSH socket created\n");

        //  Add your socket options here.

        rc = xs.xs_connect(sock, connect_to);
        if (rc == -1) {
            System.out.printf("error in xs_connect: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PUSH socket connected to %s\n", connect_to);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(size);

        System.out.printf("XS running %d iterations...\n", message_count);
        for (i = 0; i != message_count; i++) {
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
        }

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
