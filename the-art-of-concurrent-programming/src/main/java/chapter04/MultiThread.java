package chapter04;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author guojianfeng.
 * @date created in  2019/7/11
 * @desc
 */
public class MultiThread {
    /**
     * [4] Signal Dispatcher　 // 分发处理发送给JVM信号的线程
     * [3] Finalizer　　　　 // 调用对象finalize方法的线程
     * [2] Reference Handler // 清除Reference的线程
     * [1] main　 　　　　 // main线程，用户程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
                    getThreadName());
        }
    }
}
