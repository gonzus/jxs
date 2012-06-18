package io.crossroads.jni;

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
        xs = new XsLibrary();
        context = xs.xs_init();
    }

    public void dispose() {
        xs.xs_term(context);
        context = 0;
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
        Integer major = new Integer(0);
        Integer minor = new Integer(0);
        Integer patch = new Integer(0);
        int ver = xs.xs_version(major, minor, patch);
        System.out.printf("XS version is %d = %d.%d.%d\n",
                          ver,
                          major, minor, patch);
        System.out.printf("XS made version is %d\n",
                          xs.xc.makeVersion(7, 8, 9));
    }

    private void testConstants() {
        System.out.printf("Constants for v%d.%d.%d: %s=%d, %s=%d, %s=%d\n",
                          XsConstants.XS_VERSION_MAJOR,
                          XsConstants.XS_VERSION_MINOR,
                          XsConstants.XS_VERSION_PATCH,
                          "XS_PUB", XsConstants.XS_PUB,
                          "XS_PUSH", XsConstants.XS_PUSH,
                          "XS_BACKLOG", XsConstants.XS_BACKLOG);
    }

    private void testErrors() {
        System.out.printf("XS string for EADDRINUSE (%d) is [%s]\n",
                          XsErrors.EADDRINUSE, xs.xs_strerror(XsErrors.EADDRINUSE));
        System.out.printf("XS string for EINTR (%d) is [%s]\n",
                          XsErrors.EINTR, xs.xs_strerror(XsErrors.EINTR));
        System.out.printf("XS string for EFAULT (%d) is [%s]\n",
                          XsErrors.EFAULT, xs.xs_strerror(XsErrors.EFAULT));
    }

    private void testSocket() {
        int ret;
        long socket = xs.xs_socket(context, XsConstants.XS_REQ);
        System.out.printf("XS REQ socket created\n");
        int id = xs.xs_bind(socket, "tcp://127.0.0.1:6666");
        System.out.printf("XS REQ socket bound: %d\n", id);
        ret = xs.xs_shutdown(socket, id);
        System.out.printf("XS REQ socket shut down: %d\n", ret);
        ret = xs.xs_close(socket);
        System.out.printf("XS REQ socket closed: %d\n", ret);
    }

    private void testPoll() {
        long s1 = xs.xs_socket(context, XsConstants.XS_REP);
        long s2 = xs.xs_socket(context, XsConstants.XS_SUB);
        long s3 = xs.xs_socket(context, XsConstants.XS_PULL);
        int ret;

        xs.xs_bind(s1, "tcp://127.0.0.1:6666");
        xs.xs_bind(s2, "tcp://127.0.0.1:6667");
        xs.xs_bind(s3, "tcp://127.0.0.1:6668");

        XsPoller poller = new XsPoller();
        poller.addSocket(s1, XsConstants.XS_POLLIN);
        poller.addSocket(s2, XsConstants.XS_POLLIN | XsConstants.XS_POLLOUT);

        ret = poller.poll(0);
        System.out.printf("XS sockets polled 1: %d\n", ret);

        ret = poller.poll(0);
        System.out.printf("XS sockets polled 2: %d\n", ret);

        poller.addSocket(s3, XsConstants.XS_POLLOUT);
        ret = poller.poll(0);
        System.out.printf("XS sockets polled 3: %d\n", ret);

        ret = poller.poll(0);
        System.out.printf("XS sockets polled 4: %d\n", ret);
        
        poller.reset();
        poller.addSocket(s3, XsConstants.XS_POLLOUT);
        ret = poller.poll(0);
        System.out.printf("XS sockets polled 5: %d\n", ret);

        poller = null;
        System.gc();

        xs.xs_close(s3);
        xs.xs_close(s2);
        xs.xs_close(s1);
        System.out.printf("XS bye bye\n");
    }

    private XsLibrary xs = null;
    long context = 0;
}
