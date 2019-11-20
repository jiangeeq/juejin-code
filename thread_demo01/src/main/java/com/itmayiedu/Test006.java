package com.itmayiedu;

//join
public class Test006 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程,i:" + i);
                }
            }
        });
        t1.start();
        // 当在主线程当中执行到t1.join()方法时，就认为主线程应该把执行权让给t1
        t1.join();
        for (int i = 0; i < 10; i++) {
            System.out.println("main线程,i:" + i);
        }
    }
}