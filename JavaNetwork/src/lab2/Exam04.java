package lab2;

public class Exam04 {
	Object obj = new Object();
	public static void main(String[] args) {
		Exam04 e4 = new Exam04();
		MyThread mt = e4.new MyThread();
		//���ɲ������߳�
		for(int i = 1;i<=40;i++) {
			new Thread(mt,"�߳�" + i).start();
		}

	}
	
	//����counter ��
	class Counter {
	    private int c = 30;
	    public  void decrement() {
	    	if(c>0)
	    		c--;
	    	System.out.println(Thread.currentThread()+"="+c);
	    }
	}
	
	//����runuable�������̴߳���
	class MyThread implements Runnable {
		Counter counter = new Counter();
		@Override
		public synchronized void run() {
			counter.decrement();
		}	
	}
}
