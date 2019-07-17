## Java中的并发工具类
 1. [CountDownLatch（线程计数器）](#线程计数器CountDownLatch)
 2. [CyclicBarrier（同步屏障）](#同步屏障CyclicBarrier)
 3. [Semaphoree（信号量）控制并发线程数](#控制并发线程数的Semaphore)
 4. [Exchanger](#jump)
### 线程计数器CountDownLatch 
`CountDownLatch`允许一个或多个线程等待其他线程完成操作。    
例如有个需求：我们需要解析一个Excel里多个sheet的数据，此时可以考虑使用多线程，   
每个线程解析一个sheet里的数据，等到所有的sheet都解析完之后，程序需要提示解析完成。  
[查看代码](../src/main/java/chapter08/CountDownLatchTest.java)  
### 同步屏障CyclicBarrier
> CyclicBarrier的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
它要做的事情是，让一 组线程到达一个屏障（也可以叫同步点）时被阻塞，
直到最后一个线程到达屏障时，屏障才会 开门，所有被屏障拦截的线程才会继续运行。         

CyclicBarrier默认的构造方法是CyclicBarrier（int parties），其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
```text
// barrierAction参数为空
public CyclicBarrier(int parties) {
        this(parties, null);
    }
// barrierAction参数不为空
public CyclicBarrier(int parties, Runnable barrierAction) {
    if (parties <= 0) throw new IllegalArgumentException();
    this.parties = parties;
    this.count = parties;
    this.barrierCommand = barrierAction;
}
```
此处我们介绍barrierAction参数不为空的情况，因为这个方法更加高级。     
**用于在线程到达屏障时，优先执行barrierAction，方便处理更复杂的业务场景。**
[示例代码](../src/main/java/chapter08/BankWaterService.java)  
### 控制并发线程数的Semaphore
通过new Semaphore(SEMAPHORE_COUNT);来创建信号量对象   
使用acquire(int)获取许可证     
使用release(int)归还许可证
```
//创建一个含有`SEMAPHORE_COUNT`个可用许可证的信号量对象   
private static Semaphore semaphore = new Semaphore(SEMAPHORE_COUNT);    
//每个线程需要占用N许可证  
private static final int N = 2;    
public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int j = i;
            threadPool.execute(() -> {
                try {
                    //每个线程需要占用N许可证  
                    semaphore.acquire(2);
                    //模拟业务执行逻辑  
                    SleepUtils.second(4);
                    System.out.println("save data "+j);
                    //线程执行完了归还许可证
                    semaphore.release(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        threadPool.shutdown();
    }
```
Semaphore其他API
Semaphore还提供一些其他方法，具体如下。 
- int availablePermits()：返回此信号量中当前可用的许可证数。 
- int getQueueLength()：返回正在等待获取许可证的线程数。 
- boolean hasQueuedThreads()：是否有线程正在等待获取许可证。 
- void reducePermits（int reduction）：减少reduction个许可证，是个protected方法。 
- Collection getQueuedThreads()：返回所有等待获取许可证的线程集合，是个protected方 法。

### 线程间交换数据的Exchanger
Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交 换。
它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
[示例代码](../src/main/java/chapter08/ExchangerTest.java)  









