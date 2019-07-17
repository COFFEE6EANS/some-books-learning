package chapter10;

import java.util.concurrent.*;

/**
 * @author guojianfeng.
 * @date created in  2019/7/16
 * @desc
 * 当一个线程需要等待另一个线程把某个任务执行完后它才能继续执行，此时可以使用 FutureTask。
 * 假设有多个线程执行若干任务，每个任务最多只能被执行一次。
 * 当多个线程试图 同时执行同一个任务时，只允许一个线程执行任务，其他线程需要等待这个任务执行完后才 能继续执行。
 * 下面是对应的示例代码。
 */
public class FutureTaskTest {
    private final ConcurrentHashMap<Object,Future<String>> taskCache = new ConcurrentHashMap<Object, Future<String>>();
    private String executionTask(final String taskName){
        while (true){
            Future<String> future = taskCache.get(taskName);
            if (future==null){
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return taskName;
                    }
                };
                FutureTask<String> futureTask = new FutureTask<String>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);

                if (future==null){
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e){
                taskCache.remove(taskName,future);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        FutureTaskTest futureTaskTest = new FutureTaskTest();
        String task1 = futureTaskTest.executionTask("task1");
        System.out.println(task1);
    }
}
