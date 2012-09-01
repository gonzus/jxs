jxs
===

Java binding for `libxs` -- the [Crossroads IO][1] library.


What is this?
-------------

This package contains two Java bindings for `libxs`, the Crossroads IO library:

1. A deprecated binding based on [JNA][2].  This was the first
   implementation in this package, but its performance was
   unacceptable.
2. A supported binding based on [JNI][3].  The performance for this
   binding is similar (but lower) to the performance for the native
   `libxs`.

How to Use
----------

You will need the following:

1. A copy of the [JDK][4].  The binding has been tested on `v1.6.0_30`
   running on a Windows 7 machine.
2. A copy of [ant][5].  I use `v1.8.4`.
3. When using JNI (the supported option), a C compiler.  I use MSVC
   2010.
4. When using JNI (the supported option), a copy of `cpptasks`, an
   `ant` helper that automates compilation of C code.  I use `v1.0b5`.

   You can run at with `ant -lib lib/ant/cpptasks-1.0b5.jar jar` to use
   the included `cpptasks`
5. Look at the `build.<OS>.properties` file for your OS.  Pay
   particular attention to the value of: dir.jni.headers
   

Once your environment is set up and you have downloaded `jxs`, you can
do any of these:

* `ant jar` -- create all `JAR`s.
* `ant test` -- run all unit tests (none for now).
* `ant run` -- run a specific class in one of the `JAR`s.
* `ant perf` -- run performance tests.

### Debugging the build
    
It can be helpful to run the build using `ant -v` to debug compiltion issues.

```bash
ant -v -lib lib/ant/cpptasks-1.0b5.jar jar
```

### To run a basic tester class:

`ant -Dcn=io.crossroads.jni.Tester run`

To run the latency test you would have to create two command prompts
and run the following (one command in each prompt):

`ant -Dcn=io.crossroads.jni.local_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.jni.remote_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`

Similarly, for the throughput test:

`ant -Dcn=io.crossroads.jni.local_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.jni.remote_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`

Finally, for the `inproc`-based latency and throughput tests (these do
not require two command prompts):

`ant -Dcn=io.crossroads.jni.inproc_lat -Dargs="1 100000" perf`

`ant -Dcn=io.crossroads.jni.inproc_thr -Dargs="1 100000" perf`


Status
------

The XS library is about 90% supported now.  Still missing are:

* Support for setting context options (`xs_setctxopt()`).
* Support for setting / getting socket options (`xs_setsockopt()`,
  `xs_getsockopt()`).
* Support for plugins (don't know yet if this will ever be added).


License
-------

This project is released under the [LGPL][6] license, as is the native
libxs library.  See LICENSE for more details as well as the [Crossroads
I/O Licensing][7] page.


[1]: http://www.crossroads.io/                          "Crossroads I/O"
[2]: http://en.wikipedia.org/wiki/Java_Native_Access    "Java Native Access"
[3]: http://en.wikipedia.org/wiki/Java_Native_Interface "Java Native Interface"
[4]: http://en.wikipedia.org/wiki/JDK                   "Java Development Kit"
[5]: http://en.wikipedia.org/wiki/Apache_Ant            "Apache Ant"
[6]: http://www.gnu.org/licenses/lgpl.html              "LGPL"
[7]: http://www.crossroads.io/intro:license             "Crossroads I/O Licensing"
