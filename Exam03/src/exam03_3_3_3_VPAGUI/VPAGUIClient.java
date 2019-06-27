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
 * 图形化求总和，平均数、方差客户端
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
     * 利用正则表达式判断字符串是否是数字
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
     * 判断输入框是否为有效输入,若有效则返回数组信息，无效则返回null
     * @param t
     * @return
     */
	public int[] input_digit(TextField t) {
		boolean flag = true;
		//将用户输入的字符串按','分隔开
		String[] text = t.getText().split(",");
		if(!"".equals(t.getText())){//判断是否不为空字符串
			for(int i = 0 ;i<text.length;i++) {
				if(!isNumeric(text[i])&& !text[i].equals("#")) {		//如果存在输入非法(输入非数字字符)
					flag = false;
					break;
				}
			}
		}
			
		if(!flag) {
			t.setText("输入数字存在非法数值，请重新输入!!");
		}else {		//当输入合法时，此时开始讲数据封装进数组中
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
		Text text_each_num = new Text("输入数字(数字之间以英文逗号','隔开   以 ',# ' 号结尾):");
		TextField input_each_num = new TextField();
		Button submit_button = new Button("发送");
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
		
		//给按钮设置监听，当点击时检测输入框中的字符串，判断合法时，则将数据发送至服务器,再从服务器接受返回的数据
		submit_button.setOnAction((mouseClick)->{
			Socket s;
			try {
				dig = client.input_digit(input_each_num);
				if(dig!=null) {	//当输入内容合法且不为空时，将数据发送到服务器
					s = new Socket("127.0.0.1",port);	//建立套接字，创建连接
					//将套接字对象与接受结果的界面原件对象传送过去到线程中
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
				//数字信息初始化
				dig = client.input_digit(submit_button,input_each_num);
				if(dig!=null) {
					
					//将套接字对象与接受结果的界面原件对象传送过去到线程中
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
		
		//定义构造方法
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
				//定义流
				DataOutputStream out=new DataOutputStream(
						s.getOutputStream());
				//首先将数组元素个数传送过去
				out.writeInt(dig.length);
				//然后传送数字信息过去,最后一个长度信息不需要重复传送过去
				for(int i = 0;i<dig.length;i++) {
					out.writeInt(dig[i]);
				}
				Thread.sleep(10);
				DataInputStream dis = new DataInputStream(s.getInputStream()) ;
				
				//将获取结果显示在界面上
				this.sumText.setText(""+dis.readInt());
				this.aveText.setText(""+dis.readDouble());
				this.varText.setText(""+dis.readDouble());
				
				//关闭资源
				s.close();
				
				/*System.out.println("获取的数的和为："+dis.readInt());
				System.out.println("获取的数的平均数为："+dis.readDouble());
				System.out.println("获取的数的方差为："+dis.readDouble());*/
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
