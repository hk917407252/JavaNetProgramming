package lab2;

public class Exam02_1 {

	public static void main(String[] args) {
		Exam02_1 e2 = new Exam02_1();
		for(int i = 1;i<=3;i++) {
			e2.new MyThread("Ïß³Ì"+i).start();
		}
	}
	
	class MyThread extends Thread{
		
		public MyThread(String name) {
			this.setName(name);
		}
		
		
		@Override
		public void run() {
			for(int i = 1;i<=10;i++) {
				System.out.println(this.getName() + "Êä³ö" + i);
			}
		}
	}
}
