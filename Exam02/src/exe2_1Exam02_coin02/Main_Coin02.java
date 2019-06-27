package exe2_1Exam02_coin02;

public class Main_Coin02 {
	static int sum = 0;
	static int total = 0;
	public static void main(String[] args) {
		Coin_Thread c1 = new Coin_Thread("硬币一");
		Coin_Thread c2 = new Coin_Thread("硬币二");
		Coin_Thread c3 = new Coin_Thread("硬币三");
		c1.start();
		c2.start();
		c3.start();
		//等待子线程结束
		try {
			c1.join();
			c2.join();
			c3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*System.out.println("*****! *****" + total);*/
		System.out.println("综上可得，正面出现的总次数为："+sum + " 出现正面的频率为：" + (float)sum/(float)total);
	}
	
	/**
	 * 初始化静态方法用于子线程调用，计算所有子线程正面朝上的总次数
	 * @param count
	 */
	public static void sum_number(int count) {
		sum += count;	
	}
	
	/**
	 * 用于统计子线程一共抛了几次硬币
	 * @param count
	 */
	public static void count_total(int count) {
		total += count;
	}

}

//定义抛硬币硬币线程
class Coin_Thread extends Thread {
	int s = 0;
	
	
	
	/**
	 * 声明一个构造方法给硬币线程命名
	 */
	public Coin_Thread(String name) {
		// TODO Auto-generated constructor stub
		this.setName(name);
	}
	
	//重写线程run方法
	@Override
	public void run() {
		int times = 0;
		times = (int)(Math.random()*10+1);
		//模拟抛出times次硬币
		for(int i = 0;i<times;i++) {
			if(Math.random()>0.5) {
				s++;
			}
		}
		Main_Coin02.sum_number(s);
		Main_Coin02.count_total(times);
		//打印输出硬币出现正面的次数并，显示出现正面的频率
		System.out.println(this.getName()+"共抛了"+times+" 次硬币，其中 正面朝上的次数为："+ s + " 其正面出现的频率为： " + (float)s/(float)times);
	}
}
