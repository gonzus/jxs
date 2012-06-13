jxs
===

Java binding for `libxs` -- Crossroads IO


What is this?
-------------

This package contains two Java bindings for `libxs`, the Crossroads IO library:

1. A deprecated binding based on `JNA`.  This was the first
   implementation in this package, but its performance was
   unacceptable.

2. A supported binding based on `JNI`.  The performance for this
   binding is similar (but lower) to the performance for the native
   `libxs`.

How to Use
----------

You will need the following:

1. A copy of the `JDK`.  The binding has been tested on `v1.6.0_30`
   running on a Windows 7 machine.

2. A copy of `ant`.  I use `v1.8.4`.

3. When using JNI (the supported option), a C compiler.  I use MSVC
   2010.

4. When using JNI (the supported option), a copy of `cpptasks`, an
   `ant` helper that automates compilation of C code.  I use `v1.0b5`.

Once your environment is set up and you have downloaded `jxs`, you can
do any of these:

* `ant jar` -- create all `JAR`s.
* `ant test` -- run all unit tests (none for now).
* `ant run` -- run a specific class in one of the `JAR`s.
* `ant perf` -- run performance tests.

To run a basic tester class:

`ant -Dcn=io.crossroads.jni.Tester run`

To run the latency test you would have to create two command prompts
and run the following (one command in each prompt):

`ant -Dcn=io.crossroads.jni.local_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.jni.remote_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`

Similarly, for the throughput test:

`ant -Dcn=io.crossroads.jni.local_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.jni.remote_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`


Status
------

The XS library is about 80% supported now.  Still missing are:

* Support for setting context options (`xs_setctxopt()`).

* Support for setting / getting socket options (`xs_setsockopt()`,
  `xs_getsockopt()`).

* Support for I/O multiplexing (`xs_poll()`).

* Support for plugins (don't know yet if this will ever be added).


License
-------

This project is released under the LGPL license, as is the native
libxs library.  See LICENSE for more details as well as the Crossroads
I/O Licensing page.
