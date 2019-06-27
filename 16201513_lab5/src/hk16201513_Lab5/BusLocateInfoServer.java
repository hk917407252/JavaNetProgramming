package hk16201513_Lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 该类用于随机生成公交车的站台信息
 */
public class BusLocateInfoServer {
    public static void main(String[] args) throws IOException {
        while (true) {
            /** 首先获取给定的公交车站台信息 */
            InetAddress address = InetAddress.getByName("localhost");
            DatagramSocket ds1 = new DatagramSocket(6661);
            byte[] b = new byte[1024];
            DatagramPacket dp1 = new DatagramPacket(b, b.length);
            ds1.receive(dp1);
            int length = Integer.parseInt(new String(dp1.getData()).trim());

            DatagramSocket ds2 = new DatagramSocket();
            String station = "";
            /** 随机为三台公交生成站台位置信息 */
            for (int i = 0; i < 3; i++) {
                String rand = String.valueOf((int) (Math.random() * length + 1));
                if (i != 2) {
                    station += rand + ",";
                } else {
                    station += rand;
                }
            }

            /** 返回位置信息 */
            byte[] sb = station.getBytes();
            DatagramPacket dp2 = new DatagramPacket(sb, sb.length, address, 6662);
            ds2.send(dp2);
            ds1.close();
            ds2.close();
        }
    }

}
