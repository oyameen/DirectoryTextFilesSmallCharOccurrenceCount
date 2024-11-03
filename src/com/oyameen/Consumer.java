package com.oyameen;

import java.util.concurrent.Future;

public class Consumer extends Thread {

    private final Broker broker;
    private Future<?> result = null;

    public Consumer(Broker broker) {
        this.broker = broker;
    }

    public Future<?> getResult() {
        return result;
    }

    @Override
    public void run() {
        try {
            while ((broker.isContinueProducing() || !broker.getQueue().isEmpty())) {
                Runnable data = broker.getQueue().take();
                result = broker.getThreadPool().submit(data);
                System.out.println("Consumer consume data from broker.");
            }
            System.out.println("Consumer job finished its job, terminating.");
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
