package com.itmayiedu;

/**
 * Created by jiangpeng on 2018/10/31.
 */
class T1 implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().toString() + "---i:" + i);
        }
    }
}

class T2 implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().toString() + "---i:" + i);
        }
    }
}

class T3 implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().toString() + "---i:" + i);
        }
    }
}

public class JoinThreadDemo02 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new T1(), "T1");
        Thread t2 = new Thread(new T2(), "T2");
        Thread t3 = new Thread(new T3(), "T3");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}
