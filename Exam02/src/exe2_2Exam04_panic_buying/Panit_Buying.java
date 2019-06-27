package exe2_2Exam04_panic_buying;

import java.util.Random;

/*4.������50������20����Ʒ��ÿ����Ʒ�ļ۸�20-100Ԫ���ȣ���Ҫ�����£�
	ÿ�˹������Ʒ���ܹ�����3�����������Ʒ�۸��ܶ�ܹ�����100Ԫ������ʱ��֪��Ʒ�۸񣬽���ʱ��֪����Ʒ�۸���������޶�˻ز�����Ʒ���˻ز����Զ����˻غ���ٴ�������
	���дһ��Java���̳߳���ģ�Ⲣ��ӡ���������̡�
		�ܽἰ��ƹ������£�
		��20����Ʒ��ÿ��20-100Ԫ���������鰴�±�洢��Ʒ�۸�
		��ÿ�˲������������ܽ�����100
		���̣߳�ֻ������ֱ���ﵽ������Ʒ����û��ʣ����Ʒʱ���㣻
		�ܽ���ʱ������100�������ж��Ƿ���ʣ����Ʒ�����û�У������˻�������붩������Ʒֱ�����С��100�ͽ��н��㣬�����ʣ����Ʒ���������˻�������붩������Ʒֱ�����С��100�� ������������*/
 		
public class Panit_Buying {
	
	static int[] goods_stock;				//�����Ϣ
	static int[] goods_price;				//�ṩ��λ��
	static Random random = new Random();
	static int remain_goods_count = 20;		//����ʣ����Ʒ��Ŀ,��ʼ��Ϊ20
	
	//���췽��
	public Panit_Buying() {
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Panit_Buying pb = new Panit_Buying();
		goods_stock = stock_init();		//��ʼ����Ʒ���
		goods_price = price_init();		//��ʼ����Ʒ�۸�
		
		//���岢�����߳�ģ���û�
		for(int i = 1;i<=20;i++) {
			pb.new MyThread("�û�-"+i).start();
		}
		
		//��ӡ�����Ϣ
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int j = 0;j<20;j++) {
			System.out.println("��"+j+"����Ʒ�Ŀ��Ϊ"+goods_stock[j]);
		}*/
		
	}
	//�����߳���ģ�������û�
	class MyThread extends Thread{
		private String name;	
		
		//���췽��
		public MyThread(String name) {
			this.name= name;
		}
			
		@Override
		public synchronized void run() {
			int count = 0; 	//��������������Ʒ����	
			int[] brought = new int[3];	
			boolean flag = true;		//���岼��ֵȷ�ϸ��������Ƿ���δ������Ʒ,
			int paint_time = 0;
			while(true) {
				paint_time++;	//��������+1,ÿ�����������
				for(int i=1 ;i<goods_stock.length;i++) {		//�������ұ�����Ʒ��棬����������������3���ҷ��ֲ�Ϊ��治Ϊ0ʱ����������
					if(count<3) {
						
						if(goods_stock[i]!=0) {
							brought[count++] = i;				//������������Ʒ�������±꣬�������ʱ������Ʒ�۸��ͳ�Ƽ۸�		
							goods_stock[i] = 0;					//����Ʒ�۸�����㣬��ʾ����Ʒ�Ѿ���������������δ���
						}
					}else {
						if(settlement(brought,count)) {				//����ɹ��������
							System.out.println(this.getName() + ": ���˳ɹ�!!!");
							print_invoice(this.getName(),brought);   //��ӡ��Ʊ
							flag = false;
							break;
						}
						else {					//����ʧ��,��ʱcount==3,������������Ʒ
							brought[count-1]=0;	//����100Ԫ����������붩����Ʒȡ��������������������������Ʒ��-1����ע�⣬�����±���Ҫ-1���Է�Խ��
							count--;
							continue;
						}
					}
					if((i==goods_stock.length-1)&&count!=0) {		//��������Ʒ��Ŀ��Ϊ0�ҿ�����������һ����Ʒʱ
						/*System.out.println(this.getName()+"��ѯ��һ����Ʒ�嵥    -  ��ǰ������Ʒ��ĿΪ��"+count);*/
						if(settlement(brought,count)) {					//���˳ɹ�
							flag = false;
							System.out.println(this.getName()+"����ɹ�,��Ʊ��Ϣ����:");
							print_invoice(this.getName(),brought);
						}
						else {	//������ʧ��ʱ��ͬ�����ж�������
							System.out.println(this.getName()+"����ʧ��,�ܶ����100Ԫ");
							brought[count-1]=0;	//����100Ԫ����������붩����Ʒȡ��������������������������Ʒ��-1����ע�⣬�����±���Ҫ-1���Է�Խ��
							count--;
							continue;
						}
					}
				}
				
				if(count==0 && paint_time>=20) {		//��һ����Ʒ��û����
					System.out.println("�Բ���"+this.getName()+" ��Ʒ��治�㣡���´�����");
					flag = false;
				}
				
				//���û��δ������Ʒ�����˳��������������ѭ��������Ʒ�嵥
				if(flag == false) {
					break;
				}
			}	
		}
	}
	
	/**
	 * ��ӡ��Ʊ
	 */
	public void print_invoice(String name,int[] brought) {
		/* System.out.println("���ڽ��д�ӡ��Ʊ��");*/
		int sum = 0;
		for(int i= 0;i<brought.length;i++) {
			if(brought[i]!=0) {
				System.out.println(name+"������"+ brought[i]+ "����Ʒ,��Ʒ�۸�Ϊ��"+goods_price[brought[i]]);
				sum+=goods_price[brought[i]];
			}
		}
		System.out.println(name+"һ���Ļ���Ϊ�� "+ sum);
	}
	
	/**
	 * ������Ʒ������ÿ��ֻ�ܳ�����������һ����
	 * @param brought
	 */
	public void retread_good(int[] brought) {
		/*System.out.println("���ڽ��г�����");*/
		for(int i = brought.length-1;i>=0;i--) {
			//�ҵ�������붩������Ʒ�±�
			if(brought[i]!=0) {
				goods_stock[brought[i]] = 1;	//���¸���Ʒ�Ŀ����Ϣ
				break;				//����һ������֮����Ҫֹͣ��ѭ��
			}
		}
	}
	
	/**
	 * ������㷽��
	 * @param brougth
	 * @return
	 */
	public boolean settlement(int[] brougth,int count) {
		/*System.out.println("���ڽ��н��㣡");*/
		int sum = 0;
		//������������Ʒ���ܽ��
		for(int i = 0;i<brougth.length;i++) {
			if(brougth[i]!=0) {
				sum += goods_price[brougth[i]];
			}
		}
		
		if(sum<100) {		//������������ʱ�����н���
			
			return true;
		}else {				//���������100�����ж��������������ؽ���ʧ��
			retread_good(brougth);
			brougth[count-1] = 0;
			settlement(brougth,--count);
		}
		return false;
	}
	
	/**
	 * �����º���
	 * @param brought
	 *//*
	public void settlement_update_stock(int[] brought) {
		for(int i = 0;i<brought.length;i++) {	
			if(brought[i]!=0) {
				goods_stock[brought[i]]=0;
			}
		}
	}*/
	
	/**
	 * ��Ʒ����ʼ��
	 * @return
	 */
	private static int[] stock_init() {
		int[] goods_stock = new int[21];
		for(int i = 1;i<goods_stock.length;i++) {
			goods_stock[i] = 1;		//������������Ʒ����ʼ��Ϊ1
		}
		return  goods_stock;
	}
	
	/**
	 * ��Ʒ�۸��ʼ��
	 * @return
	 */
	public static int[] price_init() {
		int[] goods_price = new int[21];
		for(int i = 1;i<goods_price.length;i++) {
			goods_price[i] = random.nextInt(81)+20;
			/*System.out.println("��Ʒ "+ i+" �ļ۸�Ϊ�� " + goods[i]);*/
		}
		return goods_price; 
	}
	
}
