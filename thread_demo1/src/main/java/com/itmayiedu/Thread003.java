package com.itmayiedu;

public class Thread003 {

	public static void main(String[] args) {
		System.out.println("main... 主线程开始...");

		Thread t1 = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(" 子 i:" + i);
				}
			}
		});
		t1.start();
		for (int i = 0; i < 10; i++) {
			System.out.println("main..i:" + i);
		}
		System.out.println("main... 主线程结束...");
	}
}
