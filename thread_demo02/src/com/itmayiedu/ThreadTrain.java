package com.itmayiedu;

/**
 * 线程不安全例子
 * Created by jiangpeng on 2018/10/31.
 */

public class ThreadTrain implements Runnable {
    private int trainCount = 10;

    @Override
    public void run() {
        while (trainCount > 0) {
            try {
                Thread.sleep(500);
                sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sale() {
        if (trainCount > 0) {
            --trainCount;
            System.out.println(Thread.currentThread().getName() + ",出售第" + (10 - trainCount) + "张票");
        }
    }

    public static void main(String[] args) {
        ThreadTrain threadTrain = new ThreadTrain();
        Thread t1 = new Thread(threadTrain, "1台");
        Thread t2 = new Thread(threadTrain, "2台");
        t1.start();
        t2.start();
    }
}