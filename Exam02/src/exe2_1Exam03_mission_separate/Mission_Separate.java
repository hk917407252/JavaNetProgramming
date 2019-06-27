package exe2_1Exam03_mission_separate;

public class Mission_Separate {
	final static Object object=new Object();
	private static int sum=1;
	public static void main(String[] args) {
		Mission_Separate mul = new Mission_Separate();
		Separate_mission s1 = mul.new Separate_mission(1,3);
		Separate_mission s2 = mul.new Separate_mission(4,6);
		Separate_mission s3 = mul.new Separate_mission(7,9);
		Separate_mission s4 = mul.new Separate_mission(10,12);
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		
		System.out.println("所计算的12！结果为：" +sum);
	}
	
	class Separate_mission extends Thread{
		int begin;
		int end;
		public Separate_mission(int begin,int end) {
			// TODO Auto-generated constructor stub
			this.begin = begin;
			this.end = end;
		}
		@Override
		public void run() {
			synchronized (object) {
				for(int i = this.begin;i<=this.end;i++) {
					sum = sum*i;
				}
			}
		}
	}
}
