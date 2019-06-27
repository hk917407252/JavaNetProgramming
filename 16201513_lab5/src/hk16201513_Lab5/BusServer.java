package hk16201513_Lab5;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 公交系统服务端，提供公交路线与位置信息
 */
public class BusServer {

    /**
     * 主函数
     *
     * */
    public static void main(String[] args) throws IOException {

        /** 声明服务端套接字、连接套接字、数据写入写出流等 */
        ServerSocket serverSocket = null;
        Socket conn = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        File file = null;
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        while (true) {
            // 接收客户端查询信息
            try {
                serverSocket = new ServerSocket(6666);
                conn = serverSocket.accept();
                /** 获取客户端发送过来的查询编号 */
                dataInputStream = new DataInputStream(conn.getInputStream());
                /** 定义输出TCP流 */
                dataOutputStream = new DataOutputStream(conn.getOutputStream());
                /** 将接收数据装化为字符串 */
                String request = dataInputStream.readUTF();
                /**从文件中读取公交车的站台信息*/
                file = new File("Bus.txt");
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String route = null;
                String[] s = null;
                while ((route = bufferedReader.readLine()) != null) {
                    s = route.split(",");
                    if (s[0].equals(request)) {     /** 当匹配到请求查询的公交车编号时*/
                        break;
                    }
                }
                if (route == null) {
                    route = "";
                }
                /** 获取公交车的站台位置信息 */
                InetAddress address = InetAddress.getByName("localhost");
                /**将公交车所有站台消息发送过去*/
                DatagramSocket ds1 = new DatagramSocket();
                byte[] sb = String.valueOf(s.length - 1).getBytes();
                DatagramPacket dp1 = new DatagramPacket(sb, sb.length, address, 6661);
                ds1.send(dp1);

                /** 获取从给出的站台信息中 随机选中生成的公家车站台信息 */
                DatagramSocket ds2 = new DatagramSocket(6662);
                byte[] b = new byte[1024];
                DatagramPacket dp2 = new DatagramPacket(b, b.length);
                ds2.receive(dp2);
                String station = new String(dp2.getData()).trim();
                /** 将路线以及站台信息以TCP发送到客户端 */
                dataOutputStream.writeUTF(route);
                dataOutputStream.writeUTF(station);
                dataOutputStream.flush();     /** 将缓存推出*/
                ds1.close();
                ds2.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                /** 关闭资源 */
                dataOutputStream.close();
                dataInputStream.close();
                bufferedReader.close();
                fileInputStream.close();
                conn.close();
                serverSocket.close();
            }
        }
    }
}
