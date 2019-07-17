package chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guojianfeng.
 * @date created in  2019/7/16
 * @desc
 */
public class ExecutorTest {

    public void threadPoolExecutor() {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    }
}
