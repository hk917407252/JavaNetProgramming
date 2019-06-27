package exe2_2Exam02_deadlock;

public class DeadLock {
	
	/**
	 * 使得两个售票窗口同时售卖共有的十张票，只有完全售出了10张票才能够下班
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread("售票窗口1");
		MyThread t2 = new MyThread("售票窗口2");
		t1.start();
		t2.start();
	}
	
	
	static class MyThread extends Thread{
		//共享变量：车票
		static int tickets = 1;
		int count = 0;
		//构造方法
		public MyThread(String name) {
			this.setName(name);
		}
		@Override
		public void  run() {
			while(true) {
				if(tickets<=10) {
					System.out.println(Thread.currentThread().getName() + " 出售了 第" + (tickets++) +" 张票");
						count++;
					}
				//当一个窗口卖出十张票就不再售票
				else if(count==10) {
					System.out.println(Thread.currentThread().getName()+"成功售出10张票");
					break;
				}else {
					System.out.println(Thread.currentThread().getName()+ " 还差 " + (10-count) + " 张票才能够关闭营业");
					break;
				}
			}
		}
	}
}
