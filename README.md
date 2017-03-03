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
