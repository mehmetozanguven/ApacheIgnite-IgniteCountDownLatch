# ApacheIgnite-IgniteCountDownLatch

* Apache Ignite  is a memory-centric distributed database, caching, and processing platform for transactional, analytical, and streaming workloads delivering in-memory speeds at petabyte scale. To get more information and download Apache Ignite [here](https://ignite.apache.org/)

### Run Publisher -> Client -> Consumer ignite nodes sequentially

This is simple example about synchronization in the Ignite Nodes

In this example:
* I will create datas in the (Publisher node) cache (1.000.000 numbers)
* Then, I will create Client node which will wait for Consumer node
* Then, I will create a Consumer node which will have consume all the datas, 
   * Then it will decrease the catch
* After all Client can get data from the cache.



To lookup backup entry data, I have implemented in 2 ways. One is **Cache Metrics**, other is **CachePeek Mode**. You can use one of them.
