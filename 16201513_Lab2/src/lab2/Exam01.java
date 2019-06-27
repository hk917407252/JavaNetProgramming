package lab2;

import java.util.Scanner;

public class Exam01 {

	public static void main(String[] args) {
		int num;
		Exam01 e1 = new Exam01();
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入需要生成的线程数量:" );
		num = scan.nextInt();
		/*System.out.println("****" + num);*/
		//动态控制线程数量
		for(int i=1;i<=num;i++) {
			e1.new MyThread("线程" + i).start();;
		}

	}
	
	//定义线程
	class MyThread extends Thread{
		public MyThread(String name) {
			this.setName(name);
		}
		//控制线程生存一秒
		@Override
		public void run() {
			try {
				
				join(1000);
				System.out.println(this.getName()+"结束！");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
