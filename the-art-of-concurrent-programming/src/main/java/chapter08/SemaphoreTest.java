package chapter08;

import chapter04.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author guojianfeng.
 * @date created in  2019/7/15
 * @desc
 */
public class SemaphoreTest {
    //模拟程序的并发为30个
    private static final int THREAD_COUNT = 30;
    //模拟数据库的连接池最大连接数为10个
    private static final int SEMAPHORE_COUNT = 10;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore semaphore = new Semaphore(SEMAPHORE_COUNT);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int j = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire(2);
                    SleepUtils.second(2);
                    System.out.println("*****************");
                    System.out.println("save data " + j);
                    System.out.println("availablePermits ... " + semaphore.availablePermits());
                    System.out.println("getQueueLength ... " + semaphore.getQueueLength());
                    System.out.println("hasQueuedThreads ... " + semaphore.hasQueuedThreads());
                    System.out.println("*****************");
                    semaphore.release(2);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }

            });
        }
        threadPool.shutdown();
    }
}
