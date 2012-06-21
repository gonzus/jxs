package io.crossroads.jni;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

public class inproc_lat {
    static class Worker
        implements Runnable {
        public Worker(XsLibrary xs,
                      long ctx,
                      int roundtrip_count,
                      int message_size,
                      String addr,
                      CountDownLatch done) {
            this.xs = xs;
            this.ctx = ctx;
            this.roundtrip_count = roundtrip_count;
            this.message_size = message_size;
            this.addr = addr;
            this.done = done;
        }

        public void run() {
            try {
                do_run();
                done.countDown();
            } catch (InterruptedException ex) {
            }
        }
        
        private void do_run()
            throws InterruptedException {
            long sock;
            int rc;
            int i;
            
            sock = xs.xs_socket(ctx, XsConstants.XS_REP);
            if (sock == 0) {
                System.out.printf("error in xs_socket: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            System.out.printf("XS REP socket created\n");

            rc = xs.xs_connect(sock, addr);
            if (rc == -1) {
                System.out.printf("error in xs_connect(%s): %s\n",
                                  addr,
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            System.out.printf("XS REP socket connected to %s\n",
                              addr);

            int size = 128;
            ByteBuffer bb = ByteBuffer.allocateDirect(size);

            System.out.printf("XS running %d iterations...\n",
                              roundtrip_count);
            for (i = 0; i != roundtrip_count; ++i) {
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
        }

        private XsLibrary xs;
        private long ctx;
        private int roundtrip_count;
        private int message_size;
        private String addr;
        private CountDownLatch done;
    }
    
    public static void main(String [] args)
        throws InterruptedException {
        if (args.length != 2) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: inproc_lat <message-size> <roundtrip-count>\n");
            return;
        }
        
        XsLibrary xs = new XsLibrary();

        int roundtrip_count;
        int message_size;
        String addr = "inproc://lat_test";
        long ctx = 0;
        long sock = 0;
        int rc;
        int i;
        long watch = 0;
        long elapsed = 0;
        double latency = 0;

        message_size = Integer.parseInt(args[0]);
        roundtrip_count = Integer.parseInt(args[1]);
        System.out.printf("args: %d | %d\n",
                          message_size, roundtrip_count);

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

        rc = xs.xs_bind(sock, addr);
        if (rc == -1) {
            System.out.printf("error in xs_bind(%s): %s\n",
                              addr,
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS REQ socket bound to %s\n", addr);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(size);
        CountDownLatch done = new CountDownLatch(1);

        new Thread(new Worker(xs,
                              ctx,
                              roundtrip_count,
                              message_size,
                              addr,
                              done)).start();
        
        watch = xs.xs_stopwatch_start();
        
        System.out.printf("XS running %d iterations...\n", roundtrip_count);
        for (i = 0; i != roundtrip_count; ++i) {
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

        done.await();
        
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
