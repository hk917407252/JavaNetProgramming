package lab2;

public class Exam02_1 {
	/**
	 * 三个线程，分别输出1——10
	 * @param args
	 */
	public static void main(String[] args) {
		Exam02_1 e2 = new Exam02_1();
		for(int i = 1;i<=3;i++) {
			e2.new MyThread("线程"+i).start();
		}
	}
	
	class MyThread extends Thread{
		
		public MyThread(String name) {
			this.setName(name);
		}
		
		
		@Override
		public void run() {
			for(int i = 1;i<=10;i++) {
				System.out.println(this.getName() + "输出" + i);
			}
		}
	}
}
