package com.github.mookjp.jiexample;

public class EchoMan {

    public void start() throws InterruptedException {
        while(true) {
            System.out.println("hello.");
            Thread.sleep(1000);
        }
    }
}
