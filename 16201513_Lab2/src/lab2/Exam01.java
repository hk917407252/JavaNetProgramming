package lab2;

import java.util.Scanner;

public class Exam01 {

	public static void main(String[] args) {
		int num;
		Exam01 e1 = new Exam01();
		Scanner scan = new Scanner(System.in);
		System.out.println("��������Ҫ���ɵ��߳�����:" );
		num = scan.nextInt();
		/*System.out.println("****" + num);*/
		//��̬�����߳�����
		for(int i=1;i<=num;i++) {
			e1.new MyThread("�߳�" + i).start();;
		}

	}
	
	//�����߳�
	class MyThread extends Thread{
		public MyThread(String name) {
			this.setName(name);
		}
		//�����߳�����һ��
		@Override
		public void run() {
			try {
				
				join(1000);
				System.out.println(this.getName()+"������");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
