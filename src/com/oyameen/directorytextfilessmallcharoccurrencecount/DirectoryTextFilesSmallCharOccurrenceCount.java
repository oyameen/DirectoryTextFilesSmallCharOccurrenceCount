package com.oyameen.directorytextfilessmallcharoccurrencecount;

public class DirectoryTextFilesSmallCharOccurrenceCount {
    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        if (args.length != 1)
            throw new IllegalArgumentException("You must pass the directory name as 1st argument.");
        else {

            String directoryName = args[0];
            //String  directoryName="";

            Service service = new Service();
            service.countNoOfSmallCharOccurrence(directoryName);
        }

        System.err.println(((System.nanoTime() - startTime) / 1000000000.0) / 60);

        System.exit(0);
    }
}