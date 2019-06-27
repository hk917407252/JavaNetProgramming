package exe2_2Exam01_selltickets;

public class SellTickets {

	public static void main(String[] args) {
		//初始化三个售票窗口
		Sell_Tickets_Thread t1= new Sell_Tickets_Thread("售票窗口1");
		Sell_Tickets_Thread t2= new Sell_Tickets_Thread("售票窗口2");
		Sell_Tickets_Thread t3= new Sell_Tickets_Thread("售票窗口3");
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * 定义线程执行售票
	 * @author Administrator
	 *
	 */
	static class Sell_Tickets_Thread extends Thread{
		//定义一个构造方法
		public Sell_Tickets_Thread(String name) {
			this.setName(name);
		}
		//定义共享变量
		static int tickets = 1;
		//将售票过程上锁，即同时只能有一个窗口进行售票
		@Override
		public synchronized void run() {
			while(true) {
				if(tickets<=20) {
					//输出出售地点、购票人，以及票序号
					System.out.println(this.getName() + " 出售了第" +(tickets++) +"张票");
					;
				}
				else {
					System.out.println("票已卖光,退出售票!");
					break;
				}
			}
		}
	}

}
