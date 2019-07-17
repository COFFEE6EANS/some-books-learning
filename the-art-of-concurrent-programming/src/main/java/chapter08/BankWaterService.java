package chapter08;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guojianfeng.
 * @date created in  2019/7/17
 * @desc
 */
public class BankWaterService implements Runnable {
    private CyclicBarrier barrier = new CyclicBarrier(4, this);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            fixedThreadPool.execute(() -> {
                sheetBankWaterCount.put(Thread.currentThread().getName(), 10000);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String,Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        sheetBankWaterCount.put("result",result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
