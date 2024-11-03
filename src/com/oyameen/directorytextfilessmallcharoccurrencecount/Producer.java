package com.oyameen.directorytextfilessmallcharoccurrencecount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class Producer {
    private final int[] count;
    private final Broker broker;

    Producer(final int[] count, Broker broker) {
        this.count = count;
        this.broker = broker;
    }

    public void produce(String directoryPath) throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            walk.filter(filePath -> filePath.toString().endsWith(".txt"))
                    .forEach(txtFilePath -> {
                        try {
                            System.out.println("Producing txtFilePath = " + txtFilePath);
                            broker.getQueue().put(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Task #" + Thread.currentThread().getId() +
                                            " on txtFilePath = " + txtFilePath);

                                    try {
                                        doCountOfASCII(txtFilePath.toString());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        this.broker.setContinueProducing(Boolean.FALSE);
        System.out.println("Producer job finished, terminating.");
    }

    public void doCountOfASCII(String filePath) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        int[] fakeCount = new int[26];
        int value;
        while ((value = input.read()) != -1) {
            char c = (char) value;
            if ((c >= 'a') && (c <= 'z')) {
                fakeCount[c - 'a']++;
            }
        }
        incrementCount(fakeCount);
    }

    public synchronized void incrementCount(int[] fakeCount) {
        for (int i = 0; i < 26; i++) {
            count[i] = count[i] + fakeCount[i];
        }
    }
}
