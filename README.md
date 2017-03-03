jinterface-example
================================================================================

This is an example repository to show how Java Application code works with
[Erlang jinterface package](http://erlang.org/doc/apps/jinterface/jinterface_users_guide.html).


Reference: [inaka / From Erlang to Java and Back Again: Part 1](http://inaka.net/blog/2013/09/05/from-erlang-to-java-and-back-again-1/)

## Setup

Compile and copy java files to OTP Application directory:

```
cd java-jinterface-example
./gradlew clean build

cd ../
unzip java-jinterface-example/build/distributions/jinterface-example.zip -d erlang_jinterface_example/apps/erlang_jinterface_example/priv
```

```
cd erlang_jinterface_example
```

Run epmd as debug mode:

```
epmd -d -d -d
```

Run OTP Application:

```
rebar3 shell
```
