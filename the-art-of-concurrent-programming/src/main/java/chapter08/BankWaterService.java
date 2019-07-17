package chapter08;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guojianfeng.
 * @date created in  2019/7/17
 * @desc
 * 用一个Excel保存了用户所有银行流水，每个Sheet保存一个账户近一年的每笔银行流水，
 * 现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，
 * 最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流 水，
 */
public class BankWaterService implements Runnable {
    private CyclicBarrier barrier = new CyclicBarrier(4, this);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            final int j = i;
            fixedThreadPool.execute(() -> {
                sheetBankWaterCount.put(Thread.currentThread().getName(), 10000);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"....."+j);
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
        System.out.println(result+"   run()  ...");
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
