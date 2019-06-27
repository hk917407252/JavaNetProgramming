package class4_thread;

public class ThreadTest {

	public static void main(String[] args) {
		Thread myThread1 = new Thread(new Thread() {
			@Override
			public void run() {
				for(int i = 0;i<10;i++) {
					System.out.println("Êý×Ö: "+i);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread myThread2 = new Thread(new Thread() {
			@Override
			public void run() {
				while(myThread1.isAlive()) {
					System.out.println("ÎÒµîºó£¡");
					try {
						sleep(1000);	
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		myThread1.start();
		myThread2.start();

	}
}
