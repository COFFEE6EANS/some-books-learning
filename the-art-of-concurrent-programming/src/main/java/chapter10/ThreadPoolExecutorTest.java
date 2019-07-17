package chapter10;

import chapter04.SleepUtils;

import java.util.concurrent.*;

/**
 * @author guojianfeng.
 * @date created in  2019/7/17
 * @desc
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        long start = System.currentTimeMillis();
//        CountDownLatch countDownLatch = new CountDownLatch(10);
        CyclicBarrier barrier = new CyclicBarrier(6);
        for (int i = 0; i < 5; i++) {
            fixedThreadPool.execute(()->{
                SleepUtils.second(1);
                try {
                    System.out.println(Thread.currentThread().getName()+"before ... ");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName()+"after ... ");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });
        }
        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start)+" 秒");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

       //        fixedThreadPool.shutdown();
//        countDownLatch.await();
        long end2 = System.currentTimeMillis();
        System.out.println("耗时："+(end2-start)+" 秒,end...");
    }
}
