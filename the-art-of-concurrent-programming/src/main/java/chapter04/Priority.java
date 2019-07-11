package chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author guojianfeng.
 * @date created in  2019/7/11
 * @desc
 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws Exception {
        List<JobThread> jobs = new ArrayList<JobThread>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            JobThread job = new JobThread(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();

        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (JobThread job : jobs) {
            System.out.println("Job Priority : " + job.priority + "," + "Count : " + job.jobCount);
        }
    }

    static class JobThread implements Runnable {
        private long jobCount;
        //用于设置优先级
        private int priority;

        public JobThread(int priority) {
            this.priority = priority;
        }

        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}


