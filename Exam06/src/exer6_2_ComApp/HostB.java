package exer6_2_ComApp;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class HostB {
    static DatagramSocket ds;
    static DatagramPacket dp;
    private static int port1  = 6651;
    private static int port2  = 6652;
    private static String ip_add="127.0.0.1";

    //非对称密钥算法
    private static final String KEY_ALGORITHM = "RSA";
    //字符编码
    private static final String CHARSET = "UTF-8";

    /** 堆成算法名称 */
    private static final String KEY_ALGORITHM1 = "AES";

    /** 算法名称/加密模式/填充方式 */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /** 私钥 */
    String private_key = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAma1JH1kUTv7uWmhg4+xqJro+JYOVkQQaQoywFzzq3lxC2u7VJ/9Wpx1sCdD6N2VOIfG38QcbkoSIDayy1hQYMwIDAQABAkAQwRSsx0BwkFiaILEXiEnWaQ2nd14SDllykfYMwoZ0sXzHkYWxhExokoqPMT0zEUs1JkdZxvmlgMoY03mrQfIRAiEA3y42WgOyJ3t3eyZZygg7hOy8dzPrZJwqCX0f3R/AyDsCIQCwRo+E0HYam/BahdCs925acZlFLfVvEls5rcQsW15oaQIgBB0d81rOFdw4v5RdY1PzhOe4MZpLZHdcbwBXbgP/0bECIQCcj17iqD9tLLjRvW30YaJKhACMN+B3sQcC+Hl2qAP2mQIhAJFF2Mp09uiD2xdAiwVDpiWQ9QFaf6Lt32zhTe3uUv3W";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        HostB hostB = new HostB();
        System.out.println("这里是主机B：");
        try {
            /*while(true) {*/
            //初始化数据报套接字
            ds = new DatagramSocket(port1);		//此处端口用于接受数据

            MyThread myThread = hostB.new MyThread(ds);
            myThread.start();

            //如果上一个线程没有结束，则不继续生成线程，如果不进行等待，则无法继续绑定尚未释放的端口
			/*	while(myThread.isAlive()) {
					//设置休眠时间
					Thread.sleep(1000);
				}
			}*/
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }

    /**
     * 定义线程类
     * @author Administrator
     *
     */
    class MyThread extends Thread{
        private DatagramSocket ds;

        public MyThread(DatagramSocket ds) {
            this.ds = ds;
        }

        @Override
        public void run() {
                try {
                    //接收加密后的密码
                    //创建一个数据包，接受容器
                    byte[] by = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(by, by.length);
                    ds.receive(dp);	//调用socket的receive方法接受数据（以数据包的形式）
                    String s = new String(dp.getData(),0,dp.getData().length);
                    System.out.println("解密前的密码为："+s);

                    //用私钥，对接受的密码进行解密
                    String psd = decryptByPrivateKey(s,private_key);
                    System.out.println("解密后的密码为："+psd);
                    //将答案发送至HostA
                    //创建数据并把数据打包,将所求的总和发送到客户端
                    String content = encrypt("这里是主机B的文件内容！！！",psd);

                    content = content +","+ encrypt(getDigestMessage(),psd);        //将文件内容（已加密）与消息摘要（已加密）一同发送，用逗号隔开
                    System.out.println("HostB发送回去的文件内容与摘要信息:" + content);
                    byte[] bytes = content.getBytes();					            //将内容打包
                    InetAddress addr = InetAddress.getByName(ip_add);
                    dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//此处端口port2为目标端口，即想要对方收到数据的端口
                    //调用socket对象的发送方法发送数据包
                    ds.send(dp);
                } catch (SocketException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //关闭资源
            ds.close();
        }
    }

    /**
     * 将二进制转换成十六进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * AES加密
     *
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String password) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        try {
            // 生成密钥
            Key key = toKey(password);
            // 将String转换为二进制
            byte[] byteContent = content.getBytes("UTF-8");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 初始化为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 执行加密加密
            byte[] result = cipher.doFinal(byteContent);

            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 按照所给的密码，生成一个固定密钥
     *
     * @param password 长度必须是16
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Key toKey(String password) throws UnsupportedEncodingException {
        return new SecretKeySpec(password.getBytes("UTF-8"), KEY_ALGORITHM1);
    }


    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥字节数组
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        //数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data       Base64编码的待解密数据
     * @param privateKey Base64编码的私钥
     * @return String 解密数据
     */
    public static String decryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] key = Base64.getDecoder().decode(privateKey);

        return new String(decryptByPrivateKey(Base64.getMimeDecoder().decode(data), key), CHARSET);
        /*return new String(decryptByPrivateKey(Base64.getDecoder().decode(data), key), CHARSET);*/
    }

    /**
     * 获取160位消息摘要
     * @return
     */
    public String getDigestMessage(){
        String digestMessage = "";
        String origin_digestMessage = "Age has reached the end of the beginning of a word.";
        // 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            System.out.println(" " + messageDigest.getProvider().getInfo());
            // 开始使用算法
            messageDigest.update(origin_digestMessage.getBytes());
            // 输出算法运算结果
            digestMessage = new String(messageDigest.digest(), "UTF-8");
            System.out.println("digest:"+digestMessage);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return digestMessage;
    }
}
