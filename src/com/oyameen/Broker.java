package com.oyameen;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1500);
    private volatile Boolean continueProducing = Boolean.TRUE;

    public BlockingQueue<Runnable> getQueue() {
        return queue;
    }

    public Boolean isContinueProducing() {
        return continueProducing;
    }

    public void setContinueProducing(Boolean continueProducing) {
        this.continueProducing = continueProducing;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
