package exe2_1Exam01_coin01;

public class Main_Coin01 {

	public static void main(String[] args) {
		Coin_Thread c1 = new Coin_Thread("Ӳ��һ");
		Coin_Thread c2 = new Coin_Thread("Ӳ�Ҷ�");
		Coin_Thread c3 = new Coin_Thread("Ӳ����");
		c1.start();
		c2.start();
		c3.start();
	}
}

//������Ӳ��Ӳ���߳�
class Coin_Thread extends Thread {
	int sum = 0;
	
	//����һ�����췽����Ӳ���߳�����
	public Coin_Thread(String name) {
		// TODO Auto-generated constructor stub
		this.setName(name);
	}
	
	//��д�߳�run����
	@Override
	public void run() {
		//ģ���׳�20��Ӳ��
		for(int i = 0;i<20;i++) {
			if(Math.random()>0.5) {
				sum++;
			}
		}
		if(sum >3) {
			System.out.println(this.getName()+" ���泯�ϵĴ���Ϊ��"+ sum);
		}
	}
}
