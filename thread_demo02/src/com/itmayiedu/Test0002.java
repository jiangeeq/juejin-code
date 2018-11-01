package com.itmayiedu;

class ThreadDemo02 implements Runnable {
    // 同时多个窗口共享100
    private volatile static int count = 100;// 存放方法区中
    private static Object oj = new Object();
    public boolean flag = true;

    public void run() {
        if (flag) {
            while (count > 0) {
                sale();
            }

        } else {
            while (count > 0) {
                sale();
            }
        }
    }

    public synchronized void sale() {
        synchronized (oj) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (count > 0) {
                System.out.println(Thread.currentThread().getName() + ",出售" + (100 - count + 1) + "张票");
                count--;
            }
        }

    }
}

public class Test0002 {
    public static void main(String[] args) throws InterruptedException {
        ThreadDemo02 threadDemo02 = new ThreadDemo02();
        Thread t1 = new Thread(threadDemo02, "窗口1");
        Thread t2 = new Thread(threadDemo02, "窗口2");
        t1.start();
        Thread.sleep(40);
        threadDemo02.flag = false;
        t2.start();
    }
}
