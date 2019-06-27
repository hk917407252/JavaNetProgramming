package hk16201513_Lab5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 该类用于展示客户端界面
 */
public class BusClientGUI  extends  Application{

    /** 定义套接字、数据写入写出流 */
    private static Socket socket = null;
    private static DataOutputStream output = null;
    private static DataInputStream input = null;

    /** 主函数 */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 500, Color.WHITE);
        HBox hBox = new HBox();
        /** 定义提示text以及输入框 */
        javafx.scene.text.Text t = new javafx.scene.text.Text("请输入公交车号：");
        TextField textField = new TextField();
        textField.setMinWidth(800);
        t.setFont(new Font(18));
        hBox.getChildren().add(t);
        hBox.getChildren().add(textField);
        borderPane.setTop(hBox);
        primaryStage.setTitle("公交车线路查询系统");
        GUIWindow GUIWindow = new GUIWindow();
        borderPane.setCenter(GUIWindow);
        primaryStage.setScene(scene);
        primaryStage.show();

        /** 给输入框设置监听,设置回车时间为查询操作 */
        textField.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER: {
                    try {
                        socket = new Socket("localhost", 6666);
                        String s = textField.getText();
                        output = new DataOutputStream(socket.getOutputStream());
                        output.writeUTF(s);
                        output.flush();
                        input = new DataInputStream(socket.getInputStream());
                        /** 读取路线信息 */
                        String route = input.readUTF();
                        /** 读取站台信息 */
                        String station = input.readUTF();
                        /**打印站台路线信息*/
                        /*System.out.println(route);
                        System.out.println(station);*/
                        /** 将站台信息与路线信息打包进字符串数组 */
                        String[] result = (station+","+route).split(",");

                        /** 根据站台以及路线信息绘制图形界面 */
                        GUIWindow.refreshPane(result);
                    } catch (IOException e1) {
                        System.out.println("读取路线文件出错！");
                    } finally {
                        try {
                            socket.close();
                            output.close();
                            input.close();
                        } catch (IOException e1) {
                            System.out.println("读取路线文件出错！");
                        }
                    }
                }
                default:
                    break;
            }
        });
    }
}

