package io.crossroads.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class local_lat {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: local_lat <bind-to> <message-size> <roundtrip-count>\n");
            return;
        }
        
        XsLibrary xs = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);

        String bind_to;
        int roundtrip_count;
        int message_size;
        Pointer ctx = null;
        Pointer s = null;
        int rc;
        int i;
        XsMsg msg = new XsMsg();

        bind_to = args[0];
        message_size = Integer.parseInt(args[1]);
        roundtrip_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          bind_to, message_size, roundtrip_count);

        ctx = xs.xs_init();
        if (ctx == null) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        s = xs.xs_socket(ctx, xs.XS_REP);
        if (s == null) {
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

        rc = xs.xs_msg_init(msg);
        if (rc != 0) {
            System.out.printf("error in xs_msg_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS msg inited\n");

        System.out.printf("XS running %d iterations...\n", roundtrip_count);
        for (i = 0; i != roundtrip_count; i++) {
            rc = xs.xs_recvmsg(s, msg, 0);
            if (rc < 0) {
                System.out.printf("error in xs_recvmsg: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            long ms = xs.xs_msg_size(msg).longValue();
            if (ms != message_size) {
                System.out.printf("message of incorrect size received\n");
                return;
            }
            rc = xs.xs_sendmsg(s, msg, 0);
            if (rc < 0) {
                System.out.printf("error in xs_sendmsg: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
        }

        rc = xs.xs_msg_close(msg);
        if (rc != 0) {
            System.out.printf("error in xs_msg_close: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
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
