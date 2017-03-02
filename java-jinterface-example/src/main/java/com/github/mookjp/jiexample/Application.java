package com.github.mookjp.jiexample;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Application started.");

        EchoMan echoMan = new EchoMan();
        echoMan.start();
    }
}
