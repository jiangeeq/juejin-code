package com.itmayiedu;

/**
 * ÷ÿ≈≈–Ú
 */
class ReorderExample {
    int a = 0;
    boolean flag = false;

    public void writer() {
        a = 1; // 1
        System.out.println("a = 1;");
        flag = true; // 2
        System.out.println("writer-flag = " + flag);
        System.out.println("writer*flag = " + flag);
    }

    public void reader() {
        System.out.println("reader-flag = " + flag);
        if (flag) { // 3
            int i = a * a; // 4
            System.out.println("i:" + i);
        }
        System.out.println("reader*flag = " + flag);
    }

    public static void main(String[] args) {
        ReorderExample reorderExample = new ReorderExample();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reorderExample.writer();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reorderExample.reader();
            }
        });

        t1.start();
        t2.start();

    }
}