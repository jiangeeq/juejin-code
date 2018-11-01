package com.itmayiedu;

class Res {
	public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		};
	};

	public Integer getNumber() {
		int count = threadLocal.get() + 1;
		threadLocal.set(count);
		return count;

	}

}

public class Test0003 extends Thread {
	private Res res;

	public Test0003(Res res) {
		this.res = res;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + "," + res.getNumber());
		}
	}

	public static void main(String[] args) {
		Res res = new Res();
		Test0003 t1 = new Test0003(res);
		Test0003 t2 = new Test0003(res);
		t1.start();
		t2.start();
	}
}
