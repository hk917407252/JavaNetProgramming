package exe2_2Exam02_deadlock;

public class DeadLock {
	
	/**
	 * ʹ��������Ʊ����ͬʱ�������е�ʮ��Ʊ��ֻ����ȫ�۳���10��Ʊ���ܹ��°�
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread("��Ʊ����1");
		MyThread t2 = new MyThread("��Ʊ����2");
		t1.start();
		t2.start();
	}
	
	
	static class MyThread extends Thread{
		//�����������Ʊ
		static int tickets = 1;
		int count = 0;
		//���췽��
		public MyThread(String name) {
			this.setName(name);
		}
		@Override
		public void  run() {
			while(true) {
				if(tickets<=10) {
					System.out.println(Thread.currentThread().getName() + " ������ ��" + (tickets++) +" ��Ʊ");
						count++;
					}
				//��һ����������ʮ��Ʊ�Ͳ�����Ʊ
				else if(count==10) {
					System.out.println(Thread.currentThread().getName()+"�ɹ��۳�10��Ʊ");
					break;
				}else {
					System.out.println(Thread.currentThread().getName()+ " ���� " + (10-count) + " ��Ʊ���ܹ��ر�Ӫҵ");
					break;
				}
			}
		}
	}
}
