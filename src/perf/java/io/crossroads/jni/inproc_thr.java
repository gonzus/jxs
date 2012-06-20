package io.crossroads.jni;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

public class inproc_thr {
    static class Worker
        implements Runnable {
        public Worker(XsLibrary xs,
                      long ctx,
                      int message_count,
                      int message_size,
                      String addr,
                      CountDownLatch done) {
            this.xs = xs;
            this.ctx = ctx;
            this.message_count = message_count;
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
            long s;
            int rc;
            int i;
            
            s = xs.xs_socket(ctx, XsConstants.XS_PUSH);
            if (s == 0) {
                System.out.printf("error in xs_socket: %s\n",
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            System.out.printf("XS PUSH socket created\n");

            rc = xs.xs_connect(s, addr);
            if (rc == -1) {
                System.out.printf("error in xs_connect(%s): %s\n",
                                  addr,
                                  xs.xs_strerror(xs.xs_errno()));
                return;
            }
            System.out.printf("XS PUSH socket connected to %s\n",
                              addr);

            int size = 128;
            ByteBuffer bb = ByteBuffer.allocateDirect(size);

            System.out.printf("XS running %d iterations...\n",
                              message_count);
            for (i = 0; i != message_count; ++i) {
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
        }

        private XsLibrary xs;
        private long ctx;
        private int message_count;
        private int message_size;
        private String addr;
        private CountDownLatch done;
    }
    
    public static void main(String [] args)
        throws InterruptedException {
        if (args.length != 2) {
            System.out.printf("argc was %d\n", args.length);
            System.out.printf("usage: inproc_thr <message-size> <roundtrip-count>\n");
            return;
        }
        
        XsLibrary xs = new XsLibrary();

        int message_count;
        int message_size;
        String addr = "inproc://thr_test";
        long ctx = 0;
        long s = 0;
        int rc;
        int i;
        long watch = 0;
        long elapsed = 0;
        long throughput = 0;
        double megabits = 0.0;

        message_size = Integer.parseInt(args[0]);
        message_count = Integer.parseInt(args[1]);
        System.out.printf("args: %d | %d\n",
                          message_size, message_count);

        ctx = xs.xs_init();
        if (ctx == 0) {
            System.out.printf("error in xs_init: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS inited\n");

        s = xs.xs_socket(ctx, XsConstants.XS_PULL);
        if (s == 0) {
            System.out.printf("error in xs_socket: %s\n",
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PULL socket created\n");

        rc = xs.xs_bind(s, addr);
        if (rc == -1) {
            System.out.printf("error in xs_bind(%s): %s\n",
                              addr,
                              xs.xs_strerror(xs.xs_errno()));
            return;
        }
        System.out.printf("XS PULL socket bound to %s\n", addr);

        int size = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(size);
        CountDownLatch done = new CountDownLatch(1);

        new Thread(new Worker(xs,
                              ctx,
                              message_count,
                              message_size,
                              addr,
                              done)).start();
        
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

        watch = xs.xs_stopwatch_start();
        
        System.out.printf("XS running %d iterations...\n",
                          message_count - 1);
        for (i = 0; i != message_count - 1; ++i) {
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
        }

        elapsed = xs.xs_stopwatch_stop(watch);
        if (elapsed == 0)
            elapsed = 1;
        throughput = (long) ((double) message_count / (double) elapsed * 1000000);
        megabits = (double) (throughput * message_size * 8) / 1000000;

        done.await();
        
        System.out.printf("message size: %d [B]\n", message_size);
        System.out.printf("message count: %d\n", message_count);
        System.out.printf("mean throughput: %d [msg/s]\n", (int) throughput);
        System.out.printf("mean throughput: %.3f [Mb/s]\n", megabits);

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
