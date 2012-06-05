package io.crossroads;

import com.sun.jna.Native;

public class Tester {
    public static void main(String [] args) {
        Tester tester = new Tester();
        tester.allocate();
        tester.testLibrary();
        tester.testVersion();
        tester.dispose();
    }

    public Tester() {
    }
    
    public void allocate() {
        System.out.println("Starting Tester");
        xsLibrary = (XsLibrary) Native.loadLibrary("xs_d", XsLibrary.class);
    }

    public void dispose() {
        xsLibrary = null;
        System.out.println("Finishing Tester");
    }

    private boolean testLibrary() {
        if (xsLibrary == null) {
            System.out.println("xsLibrary is null");
            return false;
        }
        
        return true;
    }
    
    private void testVersion() {
        int[] major = new int[1];
        int[] minor = new int[1];
        int[] patch = new int[1];
        xsLibrary.xs_version(major, minor, patch);
        System.out.printf("XS version is %d.%d.%d\n",
                          major[0], minor[0], patch[0]);
    }

    private XsLibrary xsLibrary = null;
}
