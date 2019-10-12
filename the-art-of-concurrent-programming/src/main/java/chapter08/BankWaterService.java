package chapter08;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guojianfeng.
 * @date created in  2019/7/17
 * @desc 用一个Excel保存了用户所有银行流水，每个Sheet保存一个账户近一年的每笔银行流水，
 * 现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，
 * 最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流 水，
 */
public class BankWaterService implements Runnable {
    private CyclicBarrier barrier;
    //线程池不应该在此提供，线程池在实际的项目中 应是全局的 此处仅作模拟。
    private static ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();
    private int[][] data;

    public BankWaterService(int[][] data) {
        this.data = data;
        barrier = new CyclicBarrier(data.length, this);
    }

    private void count() {
        for (int i = 0; i < data.length; i++) {
            final int j = i;
            final int[] lineData = data[i];
            BankWaterService.fixedThreadPool.execute(() -> {
                //计算 线程
                int sum = 0;
                for (int d : lineData) {
                    sum += d;
                }
                sheetBankWaterCount.put(Thread.currentThread().getName(), sum);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "....." + sum);
            });

        }
    }

    /**
     * 汇总线程
     */
    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        sheetBankWaterCount.put("result", result);
        System.out.println(result + "   run()  ...");
        BankWaterService.fixedThreadPool.shutdown();
    }

    public static void main(String[] args) {
        int[][] data1 = {{1,2,3,4},{4,4,4,},{0,2,-32}};
        BankWaterService bankWaterService1 = new BankWaterService(data1);
        bankWaterService1.count();

        int[][] data2 = {{1,2,3,4},{4,4,4,},{0,2,-32}};
        BankWaterService bankWaterService2 = new BankWaterService(data2);
        bankWaterService2.count();

        int[][] data3 = {{1,2,3,4},{4,4,4,},{0,2,-32}};
        BankWaterService bankWaterService3 = new BankWaterService(data3);
        bankWaterService3.count();
    }
}
