package chapter08;


import chapter04.SleepUtils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guojianfeng.
 * @date created in  2019/7/17
 * @desc
 */
public class ExchangerTest {
    private static Exchanger<String> exchanger = new Exchanger<>();
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    public static void exe(){
        fixedThreadPool.execute(() -> {
            String A = "银行流水A";
            try {
                String exchange = exchanger.exchange(A);
                System.out.println(A+"..."+Thread.currentThread().getName() + "...." + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.execute(() -> {
            String B = "银行流水B";
            try {
                String exchange = exchanger.exchange(B);
                System.out.println(B+"..."+Thread.currentThread().getName() + "...." + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.execute(() -> {
            String C = "银行流水C";
            try {
                String exchange = exchanger.exchange(C);
                System.out.println(C+"..."+Thread.currentThread().getName() + "...." + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.execute(() -> {
            String D = "银行流水D";
            try {
                String exchange = exchanger.exchange(D);
                System.out.println(D+"..."+Thread.currentThread().getName() + "...." + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        fixedThreadPool.shutdown();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            ExchangerTest.exe();
//            SleepUtils.second(1);
        }
    }

}
