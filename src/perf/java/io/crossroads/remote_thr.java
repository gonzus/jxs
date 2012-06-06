package io.crossroads;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public class remote_thr {
    public static void main(String [] args) {
        if (args.length != 3) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: remote_thr <connect-to> <message-size> <message-count>\n");
            return;
        }
        
        XsLibrary xs = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);

        String connect_to;
        int message_count;
        int message_size;
        Pointer ctx = null;
        Pointer s = null;
        int rc;
        int i;
        XsMsg msg = new XsMsg();

        connect_to = args[0];
        message_size = Integer.parseInt(args[1]);
        message_count = Integer.parseInt(args[2]);
        System.out.printf("args: %s | %d | %d\n",
                          connect_to, message_size, message_count);
        NativeLong nl = new NativeLong(message_size);

        ctx = xs.xs_init();
        if (ctx == null) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        s = xs.xs_socket(ctx, xs.XS_PUSH);
        if (s == null) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PUSH socket created\n");

        //  Add your socket options here.

        rc = xs.xs_connect(s, connect_to);
        if (rc == -1) {
            System.out.printf("error in xs_connect: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PUSH socket connected to %s\n", connect_to);

        System.out.printf("XS running %d iterations...\n", message_count);
        for (i = 0; i != message_count; i++) {
            rc = xs.xs_msg_init_size(msg, nl);
            if (rc != 0) {
                System.out.printf("error in xs_msg_init_size: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            // memset (xs_msg_data (&msg), 0, message_size);

            rc = xs.xs_sendmsg(s, msg, 0);
            if (rc < 0) {
                System.out.printf("error in xs_sendmsg: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            rc = xs.xs_msg_close(msg);
            if (rc != 0) {
                System.out.printf("error in xs_msg_close: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
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
