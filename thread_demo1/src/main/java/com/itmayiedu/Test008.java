package com.itmayiedu;

/**
 * Created by jiangpeng on 2018/10/31.
 */
class PrioritytThread implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().toString() + "---i:" + i);
        }
    }
}
public class Test008 {
    public static void main(String[] args) {
        PrioritytThread prioritytThread = new PrioritytThread();
        Thread t1 = new Thread(prioritytThread);
        Thread t2 = new Thread(prioritytThread);
        t1.start();
        // 注意设置了优先级， 不代表每次都一定会被执行。 只是CPU调度会有限分配
        t2.start();
        t1.setPriority(10);
    }
}
