package exe2_1Exam01_coin01;

public class Main_Coin01 {

	public static void main(String[] args) {
		Coin_Thread c1 = new Coin_Thread("硬币一");
		Coin_Thread c2 = new Coin_Thread("硬币二");
		Coin_Thread c3 = new Coin_Thread("硬币三");
		c1.start();
		c2.start();
		c3.start();
	}
}

//定义抛硬币硬币线程
class Coin_Thread extends Thread {
	int sum = 0;
	
	//声明一个构造方法给硬币线程命名
	public Coin_Thread(String name) {
		// TODO Auto-generated constructor stub
		this.setName(name);
	}
	
	//重写线程run方法
	@Override
	public void run() {
		//模拟抛出20次硬币
		for(int i = 0;i<20;i++) {
			if(Math.random()>0.5) {
				sum++;
			}
		}
		if(sum >3) {
			System.out.println(this.getName()+" 正面朝上的次数为："+ sum);
		}
	}
}
