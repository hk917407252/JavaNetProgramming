package exe2_1Exam02_coin02;

public class Main_Coin02 {
	static int sum = 0;
	static int total = 0;
	public static void main(String[] args) {
		Coin_Thread c1 = new Coin_Thread("Ӳ��һ");
		Coin_Thread c2 = new Coin_Thread("Ӳ�Ҷ�");
		Coin_Thread c3 = new Coin_Thread("Ӳ����");
		c1.start();
		c2.start();
		c3.start();
		//�ȴ����߳̽���
		try {
			c1.join();
			c2.join();
			c3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*System.out.println("*****! *****" + total);*/
		System.out.println("���Ͽɵã�������ֵ��ܴ���Ϊ��"+sum + " ���������Ƶ��Ϊ��" + (float)sum/(float)total);
	}
	
	/**
	 * ��ʼ����̬�����������̵߳��ã������������߳����泯�ϵ��ܴ���
	 * @param count
	 */
	public static void sum_number(int count) {
		sum += count;	
	}
	
	/**
	 * ����ͳ�����߳�һ�����˼���Ӳ��
	 * @param count
	 */
	public static void count_total(int count) {
		total += count;
	}

}

//������Ӳ��Ӳ���߳�
class Coin_Thread extends Thread {
	int s = 0;
	
	
	
	/**
	 * ����һ�����췽����Ӳ���߳�����
	 */
	public Coin_Thread(String name) {
		// TODO Auto-generated constructor stub
		this.setName(name);
	}
	
	//��д�߳�run����
	@Override
	public void run() {
		int times = 0;
		times = (int)(Math.random()*10+1);
		//ģ���׳�times��Ӳ��
		for(int i = 0;i<times;i++) {
			if(Math.random()>0.5) {
				s++;
			}
		}
		Main_Coin02.sum_number(s);
		Main_Coin02.count_total(times);
		//��ӡ���Ӳ�ҳ�������Ĵ���������ʾ���������Ƶ��
		System.out.println(this.getName()+"������"+times+" ��Ӳ�ң����� ���泯�ϵĴ���Ϊ��"+ s + " ��������ֵ�Ƶ��Ϊ�� " + (float)s/(float)times);
	}
}
