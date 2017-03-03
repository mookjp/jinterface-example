package com.github.mookjp.jiexample;

import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpMbox;
import com.ericsson.otp.erlang.OtpNode;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;

public class Application {

    public static final String NODE_NAME = "javanode@0.0.0.0";
    public static final String COOKIE = "jinterface";

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Application started.");

        OtpNode otpNode = new OtpNode(NODE_NAME, COOKIE);
        final OtpMbox otpMbox = otpNode.createMbox("javaserver");

        Thread thread = new Thread(() -> {
            while(true) {
                System.out.println("Thread is working!");

                try {
                    OtpErlangObject msg = otpMbox.receive();
                    System.out.println(MessageFormatter.format("Java program got a message: {0}", msg));
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
}
