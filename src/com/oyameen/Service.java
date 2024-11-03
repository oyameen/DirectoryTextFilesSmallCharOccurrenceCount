package com.oyameen;

import java.util.concurrent.TimeUnit;

public class Service {

    private final int[] count;
    private final Broker broker;
    private final Consumer consumer;
    private final Producer producer;


    Service() {
        this.count = new int[26];
        this.broker = new Broker();
        this.consumer = new Consumer(broker);
        this.producer = new Producer(count, broker);
    }


    public void countNoOfSmallCharOccurrence(String directoryName) throws Exception {
        consumer.start();

        producer.produce(directoryName);

        Thread.sleep(500);
        consumer.getResult().get();

        if (broker.getQueue().isEmpty() && !this.broker.isContinueProducing())
            broker.getThreadPool().shutdown();

        broker.getThreadPool().awaitTermination(500, TimeUnit.MILLISECONDS);
        broker.getThreadPool().shutdownNow();

        for (int i = 0; i < 26; i++) {
            System.out.print((char) (i + 'a'));
            System.out.println(": " + count[i]);
        }

    }
}
