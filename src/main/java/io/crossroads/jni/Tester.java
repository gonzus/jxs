package io.crossroads.jni;

public class Tester {
    public static void main(String [] args) {
        Tester tester = new Tester();
        tester.allocate();
        tester.testLibrary();
        tester.testVersion();
        tester.testErrno();
        tester.testSocket();
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
                          major.intValue(), minor.intValue(), patch.intValue());
    }

    private void testErrno() {
        int eAddrInUse = 100;
        String msg = xs.xs_strerror(eAddrInUse);
        System.out.printf("XS string for EADDRINUSE is [%s]\n",
                          msg);
    }

    private void testSocket() {
        int ret;
        long socket = xs.xs_socket(context, XsLibrary.XS_REQ);
        System.out.printf("XS REQ socket created\n");
        int id = xs.xs_bind(socket, "tcp://127.0.0.1:6666");
        System.out.printf("XS REQ socket bound: %d\n", id);
        ret = xs.xs_shutdown(socket, id);
        System.out.printf("XS REQ socket shut down: %d\n", ret);
        ret = xs.xs_close(socket);
        System.out.printf("XS REQ socket closed: %d\n", ret);
    }

    private XsLibrary xs = null;
    long context = 0;
}
