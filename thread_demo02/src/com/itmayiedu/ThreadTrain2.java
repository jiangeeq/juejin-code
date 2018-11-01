package com.itmayiedu;

/**
 * 同步方法使用的是什么锁？
 * 答：同步函数使用this锁。
 * 证明方式: 一个线程使用同步代码块(this明锁),另一个线程使用同步函数。如果两个线程抢票不能实现同步，那么会出现数据错误。
 */
public class ThreadTrain2 implements Runnable {
    private int trainCount = 100;
    private boolean flag = true;
    private Object oj = new Object();

    @Override
    public void run() {
        if (flag) {
            while (trainCount > 0) {
                synchronized (this) {
                    if (trainCount > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {

                        }
                        System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "张票");
                        trainCount--;
                    }
                }
            }
        } else {
            while (trainCount > 0) {
                sale();
            }
        }

    }

    public synchronized void sale() {
        if (trainCount > 0) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {

            }
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "张票");
            trainCount--;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTrain2 threadTrain = new ThreadTrain2();
        Thread t1 = new Thread(threadTrain, "1号");
        Thread t2 = new Thread(threadTrain, "2号");
        t1.start();
        Thread.sleep(40);
        threadTrain.flag = false;
        t2.start();
    }

}
