package com.deverle.coding.threadmonitor.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
public class DelayedCounter implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(DelayedCounter.class);
    private String threadName;

    public void setThreadName(String threadName) {
        this.threadName = threadName;
        LOGGER.info("Init thread " + threadName);
    }

    @Override
    public void run() {
        LOGGER.info("Started thread " + threadName);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage() + " " + threadName);
        }
    }

    public static void main(String args[]) {
        (new Thread(new DelayedCounter())).start();
    }

    private void throwErrorOnRandomNumber() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 -1 ) + 1) + 1;
        if(randomNum % 3 == 0) {
            throw new IllegalStateException("!!!! error reported on " + threadName + " !!!!");
        }
    }
}
