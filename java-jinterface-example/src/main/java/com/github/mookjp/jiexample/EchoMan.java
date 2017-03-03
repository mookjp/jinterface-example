package com.github.mookjp.jiexample;

import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangLong;
import com.ericsson.otp.erlang.OtpErlangMap;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangString;
import com.ericsson.otp.erlang.OtpMbox;
import com.ericsson.otp.erlang.OtpNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EchoMan {

    public void start() throws IOException {

        String nodeName = "javanode@0.0.0.0";
        String cookie = "jinterface";

        OtpNode otpNode = new OtpNode(nodeName, cookie);
        final OtpMbox otpMbox = otpNode.createMbox("javaserver");

        Thread thread = new Thread(() -> {
            while(true) {
                System.out.println("Thread is working! Listening...");

                try {
                    OtpErlangObject msg = otpMbox.receive();

                    if (msg instanceof OtpErlangAtom) {
                        System.out.println("Java program got an atom message: " +  toString(msg));
                    } else if (msg instanceof OtpErlangString) {
                        System.out.println("Java program got a string message: " +  toString(msg));
                    } else if (msg instanceof OtpErlangMap) {
                        String mapString = Arrays.stream(((OtpErlangMap) msg).keys())
                                .map(key -> {
                                    String keyStr = toString(key);
                                    String value = toString(((OtpErlangMap) msg).get(key));
                                    return keyStr + ":" + value;
                                })
                                .collect(Collectors.joining(","));
                        System.out.println("Java program got a map message: " + mapString);
                    } else {
                        System.out.println("Java program got a non-supported message: " + msg);
                    }
                } catch (OtpErlangExit otpErlangExit) {
                    System.out.println(otpErlangExit.getMessage());
                    System.exit(1);
                } catch (OtpErlangDecodeException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        thread.start();
    }

    private String toString(OtpErlangObject o) {

        if (o instanceof OtpErlangAtom) {
            return ((OtpErlangAtom) o).atomValue();
        }
        if (o instanceof OtpErlangString) {
            return ((OtpErlangString) o).stringValue();
        }
//        if (o instanceof OtpErlangInt) {
//            return ((OtpErlangInt) o).bigIntegerValue().toString();
//        }
//        if (o instanceof OtpErlangDouble) {
//            return String.valueOf(((OtpErlangDouble) o).doubleValue());
//        }
        if (o instanceof OtpErlangLong) {
            return String.valueOf(((OtpErlangLong) o).longValue());
        }

        return "NON-SUPPORTED-VALUE";
    }
}
