package com.igniteCountDownLatch;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCountDownLatch;
import org.apache.ignite.cache.CacheMetrics;

public class Consumer {

    private static final String CONSUMER_CONFIG_PATH="consumer_configuration.xml";

    public static void main(String[] args) throws
                                    InterruptedException {

        /* To solve that:
              SEVERE: Failed to resolve default logging config file: config/java.util.logging.properties
        */
        System.setProperty("java.util.logging.config.file", "java.util.logging.properties");

        Ignite consumer = IgniteFactory.
                createIgniteNodeWithSpecificConfiguration("s", CONSUMER_CONFIG_PATH);



        //via Cache Metrics

        IgniteCache igniteCache = consumer.getOrCreateCache("sampleCache");
        CacheMetrics cacheMetrics = igniteCache.metrics();
        IgniteCountDownLatch igniteCountDownLatch = consumer.
                countDownLatch("simpleLatch",
                        1,
                        false,
                        true);
        long backupEntryCount= cacheMetrics.getOffHeapBackupEntriesCount();
        long primaryEntryCount = cacheMetrics.getOffHeapPrimaryEntriesCount();

        while (true){
            cacheMetrics = igniteCache.metrics();
            if (backupEntryCount == primaryEntryCount && backupEntryCount != 0){
                System.out.println("Consumer saw that backup entry count " +
                        "and primary count are equal");
                System.out.println("Now, Consumer decrements the count of the latch.");

                igniteCountDownLatch.countDown();
                break;
            }
            System.out.println("Looking again");
            backupEntryCount= cacheMetrics.getOffHeapBackupEntriesCount();
            primaryEntryCount = cacheMetrics.getOffHeapPrimaryEntriesCount();
            System.out.println(backupEntryCount);
            System.out.println(primaryEntryCount);
            System.out.println("------------");
        }
        
        //via Cache Metrics

        // via CachePeek Mode

/*        IgniteCache igniteCache = consumer.getOrCreateCache("sampleCache");

        TimeUnit.SECONDS.sleep(6);


        IgniteCountDownLatch igniteCountDownLatch = consumer.
                countDownLatch("simpleLatch",
                                    1,
                            false,
                                true);

        int backupEntryCount= igniteCache.size(CachePeekMode.BACKUP);
        int primaryEntryCount = igniteCache.size(CachePeekMode.PRIMARY);


        if (backupEntryCount == primaryEntryCount && backupEntryCount != 0){
            System.out.println("Consumer saw that backup entry count " +
                    "and primary count are equal");
            System.out.println("Now, Consumer decrements the count of the latch.");

            igniteCountDownLatch.countDown();
        }
        // via CachePeek Mode
*/




    }
}
