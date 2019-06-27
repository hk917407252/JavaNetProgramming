package exe2_2Exam03_purchase_and_sell;

import java.util.Random;

public class PurChase_Sell {
	static int stock = 0;
	static Random rand = new Random();
	public static void main(String[] args) {
		PurChase_Sell ps = new PurChase_Sell();
		MyThread01 m1 = new MyThread01();
		Thread m2 = new Thread( ps.new MyThread02(),"线程2");
		m1.start();
		m2.start();
	}
	
	/**
	 * 定义线程模拟售货过程
	 * @author Administrator
	 *
	 */
	class MyThread02 implements Runnable{
		int sold;
		
		public MyThread02() {
			
		}
		//声明随机数生成
		@Override
		public void run() {
			while(true) {
				if(stock>0) {
					int temp = stock;
					sold = rand.nextInt(temp)+1;
					stock-=sold;
					System.out.println("售出： " + sold + " 件商品");
				}
				//控制两次销售的时间差
				try {
					Thread.currentThread().sleep(rand.nextInt(1000)+1200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 定义线程模拟进货过程
	 * @author Administrator
	 *
	 */
	static class MyThread01 extends Thread{
		int purchase;
		@Override
		public void run() {
			while(true) {
				if(stock<10) {
					purchase = (rand.nextInt(50)+50);
					stock += purchase;
					System.out.println("进货了 " + purchase+ " 件商品");
				}
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
