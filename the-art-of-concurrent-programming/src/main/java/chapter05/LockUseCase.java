package chapter05;

import chapter04.SleepUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guojianfeng.
 * @date created in  2019/7/20
 * @desc
 */
public class LockUseCase {
    public static ExecutorService service = Executors.newFixedThreadPool(4);
    public static void main(String[] args) {
//        service.invokeAll()
    }

}
interface A extends Callable {
    public Object exec();
    @Override
    default Object call() {
        return this.exec();
    }
}
class B implements A{
    @Override
    public Object exec() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < 1000; i++) {
            total = total.add(BigDecimal.ONE);
        }
        return total;
    }
}