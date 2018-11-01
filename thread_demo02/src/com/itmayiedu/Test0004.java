package com.itmayiedu;

class ThreadDemo004 extends Thread {
	public  volatile boolean flag = true;

	@Override
	public void run() {
		System.out.println("线程开始...");
		while (flag) {

		}
		System.out.println("线程結束...");
	}
	public void setRuning(boolean flag){
		this.flag=flag;
	}
}

public class Test0004 {
   public static void main(String[] args) throws InterruptedException {
	  ThreadDemo004 threadDemo004 = new ThreadDemo004();
	  threadDemo004.start();
	  Thread.sleep(3000);
	  threadDemo004.setRuning(false);
	  System.out.println("flag已經改為false");
	  Thread.sleep(1000);
	  System.out.println("flag:"+threadDemo004.flag);
}
}
