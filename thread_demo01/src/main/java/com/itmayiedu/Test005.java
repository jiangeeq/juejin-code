package com.itmayiedu;

public class Test005 {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);

					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("我是子线程(用户线程)");
				}
			}
		});
		// 标识当前方法为守护线程
		t1.setDaemon(true);
		t1.start();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(300);
			System.out.println("main:i:" + i);
		}
		System.out.println("主线程执行完毕...");

	}

}
