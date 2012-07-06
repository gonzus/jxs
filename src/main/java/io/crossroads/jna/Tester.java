package io.crossroads.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Tester {
    public static void main(String [] args) {
        Tester tester = new Tester();
        tester.allocate();
        tester.testLibrary();
        tester.testConstants();
        tester.testErrors();
        tester.testVersion();
        tester.testSocket();
        tester.testPoll();
        tester.dispose();
    }

    public Tester() {
    }
    
    public void allocate() {
        System.out.printf("Starting Tester\n");
        xs = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);
        ctx = xs.xs_init();
    }

    public void dispose() {
        xs.xs_term(ctx);
        ctx = null;
        xs = null;
        System.out.printf("Finished Tester\n");
    }

    private boolean testLibrary() {
        if (xs == null) {
            System.out.printf("xsLibrary is null\n");
            return false;
        }
        
        return true;
    }
    
    private void testVersion() {
        System.out.printf("=== VERSION ===\n");
        int[] major = new int[1];
        int[] minor = new int[1];
        int[] patch = new int[1];
        xs.xs_version(major, minor, patch);
        System.out.printf("XS version is %d.%d.%d\n",
                          major[0], minor[0], patch[0]);
    }

    private void testConstants() {
        System.out.printf("=== CONSTANTS ===\n");
        System.out.printf("Constant %20s = %d\n",
                          "XS_PAIR", XsConstants.XS_PAIR);
        System.out.printf("Constant %20s = %d\n",
                          "XS_PUB", XsConstants.XS_PUB);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SUB", XsConstants.XS_SUB);
        System.out.printf("Constant %20s = %d\n",
                          "XS_REQ", XsConstants.XS_REQ);
        System.out.printf("Constant %20s = %d\n",
                          "XS_REP", XsConstants.XS_REP);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XREQ", XsConstants.XS_XREQ);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XREP", XsConstants.XS_XREP);
        System.out.printf("Constant %20s = %d\n",
                          "XS_PULL", XsConstants.XS_PULL);
        System.out.printf("Constant %20s = %d\n",
                          "XS_PUSH", XsConstants.XS_PUSH);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XPUB", XsConstants.XS_XPUB);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XSUB", XsConstants.XS_XSUB);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SURVEYOR", XsConstants.XS_SURVEYOR);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RESPONDENT", XsConstants.XS_RESPONDENT);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XSURVEYOR", XsConstants.XS_XSURVEYOR);
        System.out.printf("Constant %20s = %d\n",
                          "XS_XRESPONDENT", XsConstants.XS_XRESPONDENT);
        System.out.printf("Constant %20s = %d\n",
                          "XS_ROUTER", XsConstants.XS_ROUTER);
        System.out.printf("Constant %20s = %d\n",
                          "XS_DEALER", XsConstants.XS_DEALER);
        System.out.printf("Constant %20s = %d\n",
                          "XS_MAX_SOCKETS", XsConstants.XS_MAX_SOCKETS);
        System.out.printf("Constant %20s = %d\n",
                          "XS_IO_THREADS", XsConstants.XS_IO_THREADS);
        System.out.printf("Constant %20s = %d\n",
                          "XS_PLUGIN", XsConstants.XS_PLUGIN);
        System.out.printf("Constant %20s = %d\n",
                          "XS_AFFINITY", XsConstants.XS_AFFINITY);
        System.out.printf("Constant %20s = %d\n",
                          "XS_IDENTITY", XsConstants.XS_IDENTITY);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SUBSCRIBE", XsConstants.XS_SUBSCRIBE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_UNSUBSCRIBE", XsConstants.XS_UNSUBSCRIBE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RATE", XsConstants.XS_RATE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RECOVERY_IVL", XsConstants.XS_RECOVERY_IVL);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SNDBUF", XsConstants.XS_SNDBUF);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RCVBUF", XsConstants.XS_RCVBUF);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RCVMORE", XsConstants.XS_RCVMORE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_FD", XsConstants.XS_FD);
        System.out.printf("Constant %20s = %d\n",
                          "XS_EVENTS", XsConstants.XS_EVENTS);
        System.out.printf("Constant %20s = %d\n",
                          "XS_TYPE", XsConstants.XS_TYPE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_LINGER", XsConstants.XS_LINGER);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RECONNECT_IVL", XsConstants.XS_RECONNECT_IVL);
        System.out.printf("Constant %20s = %d\n",
                          "XS_BACKLOG", XsConstants.XS_BACKLOG);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RECONNECT_IVL_MAX", XsConstants.XS_RECONNECT_IVL_MAX);
        System.out.printf("Constant %20s = %d\n",
                          "XS_MAXMSGSIZE", XsConstants.XS_MAXMSGSIZE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SNDHWM", XsConstants.XS_SNDHWM);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RCVHWM", XsConstants.XS_RCVHWM);
        System.out.printf("Constant %20s = %d\n",
                          "XS_MULTICAST_HOPS", XsConstants.XS_MULTICAST_HOPS);
        System.out.printf("Constant %20s = %d\n",
                          "XS_RCVTIMEO", XsConstants.XS_RCVTIMEO);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SNDTIMEO", XsConstants.XS_SNDTIMEO);
        System.out.printf("Constant %20s = %d\n",
                          "XS_IPV4ONLY", XsConstants.XS_IPV4ONLY);
        System.out.printf("Constant %20s = %d\n",
                          "XS_KEEPALIVE", XsConstants.XS_KEEPALIVE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SURVEY_TIMEOUT", XsConstants.XS_SURVEY_TIMEOUT);
        System.out.printf("Constant %20s = %d\n",
                          "XS_MORE", XsConstants.XS_MORE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_DONTWAIT", XsConstants.XS_DONTWAIT);
        System.out.printf("Constant %20s = %d\n",
                          "XS_SNDMORE", XsConstants.XS_SNDMORE);
        System.out.printf("Constant %20s = %d\n",
                          "XS_POLLIN", XsConstants.XS_POLLIN);
        System.out.printf("Constant %20s = %d\n",
                          "XS_POLLOUT", XsConstants.XS_POLLOUT);
        System.out.printf("Constant %20s = %d\n",
                          "XS_POLLERR", XsConstants.XS_POLLERR);
    }

    private void testErrors() {
        System.out.printf("=== ERRORS ===\n");
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENOMEM", XsErrors.ENOMEM, xs.xs_strerror(XsErrors.ENOMEM));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EFAULT", XsErrors.EFAULT, xs.xs_strerror(XsErrors.EFAULT));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EINVAL", XsErrors.EINVAL, xs.xs_strerror(XsErrors.EINVAL));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EMFILE", XsErrors.EMFILE, xs.xs_strerror(XsErrors.EMFILE));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EINTR", XsErrors.EINTR, xs.xs_strerror(XsErrors.EINTR));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENAMETOOLONG", XsErrors.ENAMETOOLONG, xs.xs_strerror(XsErrors.ENAMETOOLONG));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENODEV", XsErrors.ENODEV, xs.xs_strerror(XsErrors.ENODEV));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EAGAIN", XsErrors.EAGAIN, xs.xs_strerror(XsErrors.EAGAIN));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ETIMEDOUT", XsErrors.ETIMEDOUT, xs.xs_strerror(XsErrors.ETIMEDOUT));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENOTSUP", XsErrors.ENOTSUP, xs.xs_strerror(XsErrors.ENOTSUP));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EPROTONOSUPPORT", XsErrors.EPROTONOSUPPORT, xs.xs_strerror(XsErrors.EPROTONOSUPPORT));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENOBUFS", XsErrors.ENOBUFS, xs.xs_strerror(XsErrors.ENOBUFS));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENETDOWN", XsErrors.ENETDOWN, xs.xs_strerror(XsErrors.ENETDOWN));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EADDRINUSE", XsErrors.EADDRINUSE, xs.xs_strerror(XsErrors.EADDRINUSE));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EADDRNOTAVAIL", XsErrors.EADDRNOTAVAIL, xs.xs_strerror(XsErrors.EADDRNOTAVAIL));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ECONNREFUSED", XsErrors.ECONNREFUSED, xs.xs_strerror(XsErrors.ECONNREFUSED));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EINPROGRESS", XsErrors.EINPROGRESS, xs.xs_strerror(XsErrors.EINPROGRESS));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENOTSOCK", XsErrors.ENOTSOCK, xs.xs_strerror(XsErrors.ENOTSOCK));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EAFNOSUPPORT", XsErrors.EAFNOSUPPORT, xs.xs_strerror(XsErrors.EAFNOSUPPORT));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EFSM", XsErrors.EFSM, xs.xs_strerror(XsErrors.EFSM));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ENOCOMPATPROTO", XsErrors.ENOCOMPATPROTO, xs.xs_strerror(XsErrors.ENOCOMPATPROTO));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "ETERM", XsErrors.ETERM, xs.xs_strerror(XsErrors.ETERM));
        System.out.printf("Error %20s = %9d - [%s]\n",
                          "EMTHREAD", XsErrors.EMTHREAD, xs.xs_strerror(XsErrors.EMTHREAD));
    }

    private void testSocket() {
        System.out.printf("=== SOCKET ===\n");
        int ret;
        Pointer sock = xs.xs_socket(ctx, XsConstants.XS_REQ);
        System.out.printf("XS REQ socket created\n");
        int id = xs.xs_bind(sock, "tcp://127.0.0.1:6666");
        System.out.printf("XS REQ socket bound: %d\n", id);
        ret = xs.xs_shutdown(sock, id);
        System.out.printf("XS REQ socket shut down: %d\n", ret);
        ret = xs.xs_close(sock);
        System.out.printf("XS REQ socket closed: %d\n", ret);
    }

    private void testPoll() {
        System.out.printf("=== POLL ===\n");

        Pointer sock1 = xs.xs_socket(ctx, XsConstants.XS_REP);
        Pointer sock2 = xs.xs_socket(ctx, XsConstants.XS_SUB);
        Pointer sock3 = xs.xs_socket(ctx, XsConstants.XS_PULL);
        int ret;
        int j;
        int n;

        xs.xs_bind(sock1, "tcp://127.0.0.1:6666");
        xs.xs_bind(sock2, "tcp://127.0.0.1:6667");
        xs.xs_bind(sock3, "tcp://127.0.0.1:6668");

        XsPollItem[] items = (XsPollItem[]) new XsPollItem().toArray(3);
        items[0].socket = sock1;
        items[0].events = XsConstants.XS_POLLIN;
        items[1].socket = sock2;
        items[1].events = (short) (XsConstants.XS_POLLIN | XsConstants.XS_POLLOUT);

        n = 2;
        ret = xs.xs_poll(items, n, 0);
        System.out.printf("XS sockets polled 1: %d\n", ret);
        for (j = 0; j < n; ++j)
            System.out.printf("XS   item[%2d]: sock = %8x | einp = %4d | eout = %4d\n",
                              j, Pointer.nativeValue(items[j].socket),
                              items[j].events, items[j].revents);

        ret = xs.xs_poll(items, 2, 0);
        System.out.printf("XS sockets polled 2: %d\n", ret);
        for (j = 0; j < 2; ++j)
            System.out.printf("XS   item[%2d]: sock = %8x | einp = %4d | eout = %4d\n",
                              j, Pointer.nativeValue(items[j].socket),
                              items[j].events, items[j].revents);

        n = 3;
        items[2].socket = sock3;
        items[2].events = XsConstants.XS_POLLOUT;
        ret = xs.xs_poll(items, n, 0);
        System.out.printf("XS sockets polled 3: %d\n", ret);
        for (j = 0; j < n; ++j)
            System.out.printf("XS   item[%2d]: sock = %8x | einp = %4d | eout = %4d\n",
                              j, Pointer.nativeValue(items[j].socket),
                              items[j].events, items[j].revents);

        n = 3;
        ret = xs.xs_poll(items, n, 0);
        System.out.printf("XS sockets polled 4: %d\n", ret);
        for (j = 0; j < n; ++j)
            System.out.printf("XS   item[%2d]: sock = %8x | einp = %4d | eout = %4d\n",
                              j, Pointer.nativeValue(items[j].socket),
                              items[j].events, items[j].revents);

        n = 1;
        items[0].socket = sock3;
        items[0].events = XsConstants.XS_POLLOUT;
        ret = xs.xs_poll(items, n, 0);
        System.out.printf("XS sockets polled 5: %d\n", ret);
        for (j = 0; j < n; ++j)
            System.out.printf("XS   item[%2d]: sock = %8x | einp = %4d | eout = %4d\n",
                              j, Pointer.nativeValue(items[j].socket),
                              items[j].events, items[j].revents);

        items = null;
        System.gc();

        xs.xs_close(sock3);
        xs.xs_close(sock2);
        xs.xs_close(sock1);
    }

    private XsLibrary xs = null;
    private Pointer ctx = null;
}
