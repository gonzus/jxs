jxs
===

Java binding for `libxs` -- Crossroads IO


What is this?
-------------

This is a `JNA`-based Java binding for `libxs`, the Crossroads IO library.


How to Use
----------

You will need the `JDK` (I use 1.6.0_30 on Windows 7) and `Ant` (I use
1.8.4); there is no need for any other tools.  I have also included
the `JNA` libraries in the `lib` directory (version 3.4.0), so you
won't have to get them yourself; if you want to do it, go to
https://github.com/twall/jna.

Once downloaded, you can do any of these:

* `ant jar` -- create all `JAR`s.
* `ant test` -- run all unit tests (none for now).
* `ant run` -- run a specific class in one of the `JAR`s (not very
  useful).
* `ant perf` -- run a performance test (for now, only latency test is
  supported).

In particular, to run the latency test you would have to create two
command prompts and run the following (one command in each prompt):

`ant -Dcn=io.crossroads.local_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.remote_lat -Dargs="tcp://127.0.0.1:5556 1 100000" perf`

Similarly, for the throughput test:

`ant -Dcn=io.crossroads.local_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`
`ant -Dcn=io.crossroads.remote_thr -Dargs="tcp://127.0.0.1:5556 1 100000" perf`


Status
------

The XS library is almost 100% supported now.  This includes support
for `xs_poll()`, although this has not been thoroughly tested.

Still missing are:

* Support for plugins (don't know yet if this will ever be added).


License
-------

This project is released under the LGPL license, as is the native
libxs library.  See LICENSE for more details as well as the Crossroads
I/O Licensing page.
