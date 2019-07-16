package chapter08;

import chapter04.SleepUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author guojianfeng.
 * @date created in  2019/7/16
 * @desc
 */
public class CountDownLatchTest {
    private static CountDownLatch count = new CountDownLatch(3);

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread()+" ...");
                SleepUtils.second(2);
                count.countDown();
            }).start();
        }
        try {
            System.out.println("await start ...");
            count.await(10,TimeUnit.SECONDS);
            System.out.println("await finish ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
