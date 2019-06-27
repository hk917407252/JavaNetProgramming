package lab2;

public class Exam02_2 {

	public static void main(String[] args) {
		Exam02_2 e2 = new Exam02_2();
		MyRunable mr = e2.new MyRunable();
		for(int i=1;i<=3;i++) {
			Thread a = new Thread(mr,"线程"+i);
			a.setName("线程" + i);
			a.start();
		}
	}
	
	class MyRunable implements Runnable{
			
		@Override
		public void run() {
			for(int i=1;i<=10;i++) {
				System.out.println(Thread.currentThread().getName() + " : "+i);
			}
		}
	}
}
