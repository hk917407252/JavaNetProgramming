package exe2_2Exam04_panic_buying;

import java.util.Random;

/*4.设现有50人抢购20件商品（每件商品的价格20-100元不等），要求如下：
	每人购买的商品不能够超过3件；购买的商品价格总额不能够超过100元；抢购时不知商品价格，结账时才知道商品价格；如果超过限额将退回部分商品，退回策略自定，退回后可再次抢购。
	请编写一个Java多线程程序，模拟并打印出抢购过程。
		总结及设计过程如下：
		①20件商品，每件20-100元，采用数组按下标存储商品价格
		②每人不超过三件，总金额不超过100
		③线程，只管抢，直到达到三件商品或者没有剩余商品时结算；
		④结算时金额大于100则，首先判断是否有剩余商品，如果没有，依次退回最近加入订单的商品直到金额小于100就进行结算，如果有剩余商品，则依次退回最近加入订单的商品直到金额小于100就 继续加入抢购*/
 		
public class Panit_Buying {
	
	static int[] goods_stock;				//库存信息
	static int[] goods_price;				//提供价位表
	static Random random = new Random();
	static int remain_goods_count = 20;		//定义剩余商品数目,初始化为20
	
	//构造方法
	public Panit_Buying() {
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Panit_Buying pb = new Panit_Buying();
		goods_stock = stock_init();		//初始化商品库存
		goods_price = price_init();		//初始化商品价格
		
		//定义并启动线程模拟用户
		for(int i = 1;i<=20;i++) {
			pb.new MyThread("用户-"+i).start();
		}
		
		//打印库存信息
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int j = 0;j<20;j++) {
			System.out.println("第"+j+"件物品的库存为"+goods_stock[j]);
		}*/
		
	}
	//定义线程类模拟抢购用户
	class MyThread extends Thread{
		private String name;	
		
		//构造方法
		public MyThread(String name) {
			this.name= name;
		}
			
		@Override
		public synchronized void run() {
			int count = 0; 	//声明变量进行商品计数	
			int[] brought = new int[3];	
			boolean flag = true;		//定义布尔值确认该消费者是否还有未付款商品,
			int paint_time = 0;
			while(true) {
				paint_time++;	//抢购次数+1,每个人最多三轮
				for(int i=1 ;i<goods_stock.length;i++) {		//从左往右遍历商品库存，当抢购数量不大于3，且发现不为库存不为0时，继续抢购
					if(count<3) {
						
						if(goods_stock[i]!=0) {
							brought[count++] = i;				//保存所购买商品的索引下标，方便结算时根据商品价格表统计价格		
							goods_stock[i] = 0;					//将商品价格表置零，表示该商品已经被抢单（可能尚未付款）
						}
					}else {
						if(settlement(brought,count)) {				//结算成功，则结束
							System.out.println(this.getName() + ": 结账成功!!!");
							print_invoice(this.getName(),brought);   //打印发票
							flag = false;
							break;
						}
						else {					//结账失败,此时count==3,抢购了三件商品
							brought[count-1]=0;	//超过100元，将最近加入订单商品取消，更新已抢订单，把已抢商品数-1——注意，数组下标需要-1，以防越界
							count--;
							continue;
						}
					}
					if((i==goods_stock.length-1)&&count!=0) {		//当抢购物品数目不为0且看到浏览到最后一件物品时
						/*System.out.println(this.getName()+"查询满一次商品清单    -  当前抢购商品数目为："+count);*/
						if(settlement(brought,count)) {					//结账成功
							flag = false;
							System.out.println(this.getName()+"结算成功,发票信息如下:");
							print_invoice(this.getName(),brought);
						}
						else {	//当结算失败时，同样进行订单撤销
							System.out.println(this.getName()+"结算失败,总额大于100元");
							brought[count-1]=0;	//超过100元，将最近加入订单商品取消，更新已抢订单，把已抢商品数-1——注意，数组下标需要-1，以防越界
							count--;
							continue;
						}
					}
				}
				
				if(count==0 && paint_time>=20) {		//当一件商品都没抢到
					System.out.println("对不起"+this.getName()+" 商品库存不足！请下次再来");
					flag = false;
				}
				
				//如果没有未付款商品，则退出抢购，否则继续循环遍历商品清单
				if(flag == false) {
					break;
				}
			}	
		}
	}
	
	/**
	 * 打印发票
	 */
	public void print_invoice(String name,int[] brought) {
		/* System.out.println("正在进行打印发票！");*/
		int sum = 0;
		for(int i= 0;i<brought.length;i++) {
			if(brought[i]!=0) {
				System.out.println(name+"购买了"+ brought[i]+ "号商品,商品价格为："+goods_price[brought[i]]);
				sum+=goods_price[brought[i]];
			}
		}
		System.out.println(name+"一共的花销为： "+ sum);
	}
	
	/**
	 * 撤销商品订单（每次只能撤销最近加入的一单）
	 * @param brought
	 */
	public void retread_good(int[] brought) {
		/*System.out.println("正在进行撤销！");*/
		for(int i = brought.length-1;i>=0;i--) {
			//找到最近加入订单的商品下标
			if(brought[i]!=0) {
				goods_stock[brought[i]] = 1;	//更新该商品的库存信息
				break;				//撤销一个订单之后需要停止该循环
			}
		}
	}
	
	/**
	 * 定义结算方法
	 * @param brougth
	 * @return
	 */
	public boolean settlement(int[] brougth,int count) {
		/*System.out.println("正在进行结算！");*/
		int sum = 0;
		//计算所抢购商品的总金额
		for(int i = 0;i<brougth.length;i++) {
			if(brougth[i]!=0) {
				sum += goods_price[brougth[i]];
			}
		}
		
		if(sum<100) {		//当结算金额满足时，进行结算
			
			return true;
		}else {				//当结算金额超过100，进行订单撤销，并返回结算失败
			retread_good(brougth);
			brougth[count-1] = 0;
			settlement(brougth,--count);
		}
		return false;
	}
	
	/**
	 * 库存更新函数
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
	 * 商品库存初始化
	 * @return
	 */
	private static int[] stock_init() {
		int[] goods_stock = new int[21];
		for(int i = 1;i<goods_stock.length;i++) {
			goods_stock[i] = 1;		//将该索引下商品库存初始化为1
		}
		return  goods_stock;
	}
	
	/**
	 * 商品价格初始化
	 * @return
	 */
	public static int[] price_init() {
		int[] goods_price = new int[21];
		for(int i = 1;i<goods_price.length;i++) {
			goods_price[i] = random.nextInt(81)+20;
			/*System.out.println("商品 "+ i+" 的价格为： " + goods[i]);*/
		}
		return goods_price; 
	}
	
}
