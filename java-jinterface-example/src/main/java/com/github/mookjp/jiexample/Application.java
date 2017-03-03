package com.github.mookjp.jiexample;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("Application started.");
        EchoMan echoMan = new EchoMan();
        echoMan.start();
    }
}
