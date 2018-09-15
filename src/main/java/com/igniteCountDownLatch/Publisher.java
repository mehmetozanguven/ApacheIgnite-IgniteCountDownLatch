package com.igniteCountDownLatch;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;


public class Publisher {
    private static final String PUBLISHER_CONFIG_PATH="publisher_configuration.xml";
    public static void populateCache(IgniteCache igniteCache){
        for (int i = 0; i < 1000000; i++){
            igniteCache.put(i, String.valueOf(i));
        }
    }

    public static void main(String[] args) {
        /* To solve that:
              SEVERE: Failed to resolve default logging config file: config/java.util.logging.properties
        */
        System.setProperty("java.util.logging.config.file", "java.util.logging.properties");
        Ignite publisher = IgniteFactory.
                createIgniteNodeWithSpecificConfiguration("s", PUBLISHER_CONFIG_PATH);

        IgniteCache igniteCache = publisher.getOrCreateCache("sampleCache");

        populateCache(igniteCache);
        System.out.println(igniteCache.size());

    }
}
