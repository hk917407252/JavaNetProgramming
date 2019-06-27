package exam03_3_3_3_VPAGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exam03_3_2_3_variance_plus_ave.VPAClient;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * ͼ�λ����ܺͣ�ƽ����������ͻ���
 * @author Administrator
 *
 */
public class VPAGUIClient  extends Application{
	private static int port = 8811;
	private static int inputNums = 3;
	private static GridPane gridpane;
	private static Pane pane;
	private static int count = 0;
	private static int[] dig = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}
	
	
	/**
     * ����������ʽ�ж��ַ����Ƿ�������
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }
    
    /**
     * �ж�������Ƿ�Ϊ��Ч����,����Ч�򷵻�������Ϣ����Ч�򷵻�null
     * @param t
     * @return
     */
	public int[] input_digit(TextField t) {
		boolean flag = true;
		//���û�������ַ�����','�ָ���
		String[] text = t.getText().split(",");
		if(!"".equals(t.getText())){//�ж��Ƿ�Ϊ���ַ���
			for(int i = 0 ;i<text.length;i++) {
				if(!isNumeric(text[i])&& !text[i].equals("#")) {		//�����������Ƿ�(����������ַ�)
					flag = false;
					break;
				}
			}
		}
			
		if(!flag) {
			t.setText("�������ִ��ڷǷ���ֵ������������!!");
		}else {		//������Ϸ�ʱ����ʱ��ʼ�����ݷ�װ��������
			dig = new int[text.length-1];
			for(int i = 0;i<dig.length;i++) {
				dig[i] = Integer.parseInt(text[i]);
			}
			count++;
		}
	return dig;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		pane = new Pane();
		gridpane =  new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		Text text_each_num = new Text("��������(����֮����Ӣ�Ķ���','����   �� ',# ' �Ž�β):");
		TextField input_each_num = new TextField();
		Button submit_button = new Button("����");
		gridpane.add(text_each_num,0,0,3,1);
		gridpane.add(input_each_num,0,1,2,1);
		gridpane.add(submit_button,2,1);
		gridpane.add(new Text("sum: "), 0, 2);
		Text sumText = new Text();
		gridpane.add(sumText, 1, 2);
		
		gridpane.add(new Text("ave: "), 0, 3);
		Text aveText = new Text();
		gridpane.add(aveText, 1, 3);
		
		gridpane.add(new Text("var: "), 0, 4);
		Text varText = new Text();
		gridpane.add(varText, 1, 4);
		
		
		pane.getChildren().add(gridpane);
		
		int nums = 0;
		VPAGUIClient client = new VPAGUIClient();
		primaryStage.setTitle("VPAGUI");
		Scene scene = new Scene(pane,600,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//����ť���ü����������ʱ���������е��ַ������жϺϷ�ʱ�������ݷ�����������,�ٴӷ��������ܷ��ص�����
		submit_button.setOnAction((mouseClick)->{
			Socket s;
			try {
				dig = client.input_digit(input_each_num);
				if(dig!=null) {	//���������ݺϷ��Ҳ�Ϊ��ʱ�������ݷ��͵�������
					s = new Socket("127.0.0.1",port);	//�����׽��֣���������
					//���׽��ֶ�������ܽ���Ľ���ԭ�������͹�ȥ���߳���
					MyThread myThread = client.new MyThread(s,sumText,aveText,varText);
					myThread.start();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		/*while(count<inputNums) {
			try {
				//������Ϣ��ʼ��
				dig = client.input_digit(submit_button,input_each_num);
				if(dig!=null) {
					
					//���׽��ֶ�������ܽ���Ľ���ԭ�������͹�ȥ���߳���
					MyThread myThread = client.new MyThread(s,sumText,aveText,varText);
					myThread.start();
				}
			Thread.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}
	
	class MyThread extends Thread{
		private Socket s;
		private Text sumText;
		private Text aveText;
		private Text varText;
		
		//���幹�췽��
		public MyThread(Socket s,Text st,Text at,Text vt) {
			// TODO Auto-generated constructor stub
			this.s= s;
			this.sumText = st;
			this.aveText = at;
			this.varText = vt;
		}
		
		@Override
		public void run() {
			try {
				//������
				DataOutputStream out=new DataOutputStream(
						s.getOutputStream());
				//���Ƚ�����Ԫ�ظ������͹�ȥ
				out.writeInt(dig.length);
				//Ȼ����������Ϣ��ȥ,���һ��������Ϣ����Ҫ�ظ����͹�ȥ
				for(int i = 0;i<dig.length;i++) {
					out.writeInt(dig[i]);
				}
				Thread.sleep(10);
				DataInputStream dis = new DataInputStream(s.getInputStream()) ;
				
				//����ȡ�����ʾ�ڽ�����
				this.sumText.setText(""+dis.readInt());
				this.aveText.setText(""+dis.readDouble());
				this.varText.setText(""+dis.readDouble());
				
				//�ر���Դ
				s.close();
				
				/*System.out.println("��ȡ�����ĺ�Ϊ��"+dis.readInt());
				System.out.println("��ȡ������ƽ����Ϊ��"+dis.readDouble());
				System.out.println("��ȡ�����ķ���Ϊ��"+dis.readDouble());*/
				/*out.close();
				dis.close();*/
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
