package hk16201513_Lab5;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 定义图形界面类，该类负责绘制用户查询路线界面
 */
class GUIWindow extends Pane {
    /** 构造方法 */
    public GUIWindow() {

    }

    /**
     * 界面绘制方法
     * @param result
     */
    public void refreshPane(String[] result) {
        /**清空界面*/
        getChildren().clear();
        if (result.length > 3) {
            HBox hBox = new HBox();
            int station1 = Integer.parseInt(result[0]);
            int station2 = Integer.parseInt(result[1]);
            int station3 = Integer.parseInt(result[2]);
            for (int i = 4; i < result.length; i++) {
                int station = i - 3;
                VBox vBox = new VBox();
                /** 绘制矩形模拟公交车 */
                if (station == station1 || station == station2 || station == station3) {
                    Rectangle r = new Rectangle(15, 15);
                    r.setFill(Color.GREEN);
                    vBox.getChildren().add(r);
                } else {
                    Rectangle r = new Rectangle(15, 15);
                    r.setFill(Color.WHITE);
                    vBox.getChildren().add(r);
                }
                Label label = new Label("" + station);
                vBox.getChildren().add(label);
                /** 绘制圆圈模拟公交站台 */
                Circle circle = new Circle(6);
                circle.setFill(Color.PURPLE);
                vBox.getChildren().add(circle);

                Text text = new Text(result[i]);
                text.setWrappingWidth(3);
                text.setLayoutX(18);
                text.setLayoutY(150);
                text.setFont(new Font("宋体", 12));
                text.setFill(Color.web("#0076a3"));
                vBox.getChildren().add(text);
                hBox.getChildren().add(vBox);

                if (i != result.length - 1) {
                    VBox vbox = new VBox();
                    Rectangle r2 = new Rectangle(12.5, 32);
                    r2.setFill(Color.WHITE);
                    vbox.getChildren().add(r2);
                    Rectangle r3= new Rectangle(15, 5);
                    r3.setFill(Color.PURPLE);
                    vbox.getChildren().add(r3);
                    hBox.getChildren().add(vbox);
                }
            }
            getChildren().add(hBox);
        } else {
            Text text = new Text("无该公交车路线信息，请重新输入!!!");
            text.setLayoutX(440);
            text.setLayoutY(300);
            text.setFont(new Font("宋体", 30));
            text.setFill(Color.web("#0076a3"));
            getChildren().add(text);
        }
    }
}