package com.igniteCountDownLatch;

import org.apache.ignite.*;


public class Client {
    private static final String CLIENT_CONFIG_PATH="client_configuration.xml";
    public static void main(String[] args) {
        /* To solve that:
              SEVERE: Failed to resolve default logging config file: config/java.util.logging.properties
        */
        System.setProperty("java.util.logging.config.file", "java.util.logging.properties");
        Ignite client = IgniteFactory.
               createIgniteNodeWithSpecificConfiguration("c", CLIENT_CONFIG_PATH);



        IgniteCountDownLatch latch = client.countDownLatch("simpleLatch",
                                                        1,
                                                false,
                                                true);
        IgniteCache igniteCache = client.getOrCreateCache("sampleCache");
        System.out.println("Client is waiting to Consumer nodes");

        latch.await();


        System.out.println("Now client can get values");
        System.out.println(igniteCache.get(999999));
    }
}








