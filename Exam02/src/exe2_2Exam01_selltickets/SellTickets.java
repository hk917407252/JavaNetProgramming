package exe2_2Exam01_selltickets;

public class SellTickets {

	public static void main(String[] args) {
		//��ʼ��������Ʊ����
		Sell_Tickets_Thread t1= new Sell_Tickets_Thread("��Ʊ����1");
		Sell_Tickets_Thread t2= new Sell_Tickets_Thread("��Ʊ����2");
		Sell_Tickets_Thread t3= new Sell_Tickets_Thread("��Ʊ����3");
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * �����߳�ִ����Ʊ
	 * @author Administrator
	 *
	 */
	static class Sell_Tickets_Thread extends Thread{
		//����һ�����췽��
		public Sell_Tickets_Thread(String name) {
			this.setName(name);
		}
		//���干�����
		static int tickets = 1;
		//����Ʊ������������ͬʱֻ����һ�����ڽ�����Ʊ
		@Override
		public synchronized void run() {
			while(true) {
				if(tickets<=20) {
					//������۵ص㡢��Ʊ�ˣ��Լ�Ʊ���
					System.out.println(this.getName() + " �����˵�" +(tickets++) +"��Ʊ");
					;
				}
				else {
					System.out.println("Ʊ������,�˳���Ʊ!");
					break;
				}
			}
		}
	}

}
