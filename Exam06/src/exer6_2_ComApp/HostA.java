package exer6_2_ComApp;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

public class HostA {
    static DatagramSocket datagramSocket;
    static DatagramPacket datagramPacket;
    private static int port1  = 6651;
    private static int port2  = 6652;
    private static String ip_add="127.0.0.1";

    //非对称密钥算法
    private static final String KEY_ALGORITHM = "RSA";
    //字符编码
    private static final String CHARSET = "UTF-8";

    /** 堆成算法名称 */
    private static final String KEY_ALGORITHMAES = "AES";

    /** 算法名称/加密模式/填充方式 */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /** 公钥 */
    static String public_key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJmtSR9ZFE7+7lpoYOPsaia6PiWDlZEEGkKMsBc86t5cQtru1Sf/VqcdbAnQ+jdlTiHxt/EHG5KEiA2sstYUGDMCAwEAAQ==";

    static String psd = "";

    //根据给定内容生成消息摘要
    String digestMessage = getDigestMessage();

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥字节数组
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成公钥
        PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
        //数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey Base64编码的公钥
     * @return String Base64编码的加密数据
     */
    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] key = Base64.getDecoder().decode(publicKey);
        return Base64.getEncoder().encodeToString(encryptByPublicKey(data.getBytes(CHARSET), key));
    }

    /**
     * 将十六进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
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
     * 按照所给的密码，生成一个固定密钥
     *
     * @param password 长度必须是16
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Key toKey(String password) throws UnsupportedEncodingException {
        return new SecretKeySpec(password.getBytes("UTF-8"), KEY_ALGORITHMAES);
    }
   /* private static Key toKey(String password) throws UnsupportedEncodingException {
        if (null == password || password.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        random.setSeed(password.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            try {
                throw new NoSuchAlgorithmException();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return key2;
    }*/


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
     * 解密
     *
     * @param content
     * 待解密内容
     * @param password
     * 解密密钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String password) {
        if (StringUtils.isBlank(content)) {
            return "";
        }

        try {
            // 将十六进制转换为二进制
            byte[] contentHex = parseHexStr2Byte(content);

            // 生成密钥
            Key key = toKey(password);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 初始化解码模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] result = cipher.doFinal(contentHex);

            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
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
           /* System.out.println("digest:"+digestMessage);*/
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return digestMessage;
    }

    /**
     * 定义线程类
     * @author Administrator
     *
     */
    class MyThread extends Thread{
        private DatagramSocket ds;
        private int count = 0;
        //构造方法
        public MyThread(DatagramSocket ds,String message){
            this.ds = ds;
        }

        @Override
        public void run() {
                try {
                    //发送请求,将随机密码发送至B主机
                    System.out.println("HOSTA发送的密码为："+psd);
                    String psd_encoded = encryptByPublicKey(psd,public_key);    //将密码用公钥加密
                    byte[] by1 = psd_encoded.getBytes();		                //之后，转化为byte[]类型
                    System.out.println("加密后的密码为:"+psd_encoded);
                    InetAddress addr;
                    addr = InetAddress.getByName(ip_add);
                    datagramPacket = new DatagramPacket(by1, by1.length,addr,port1);	//此处port1未目标端口
                    this.ds.send(datagramPacket);

                    //接受文件与消息摘要
                    byte[] by2 = new byte[1024];					            //创建接受容器
                    datagramPacket = new DatagramPacket(by2, by2.length);		//创建数据报
                    this.ds.receive(datagramPacket);							//使用数据报接受数据
                    //将接收到的数据按英文逗号分开并存储在字符串数组中
                    String[] rece = new String(datagramPacket.getData(),0,datagramPacket.getLength()).split(",");
                    System.out.println("接收到的加密后的文件信息为:"+rece[0]);
                    System.out.println("接收到的加密后的摘要信息为:"+rece[1]);

                    String file_content = decrypt(rece[0].trim(),psd);
                    System.out.println("接收到的解密后的文件信息为:"+file_content);
                    String rece_digestMessage = decrypt(rece[1].trim(),psd);
                    System.out.println("接收到的解密后的摘要信息为:"+rece_digestMessage);

                    //判断摘要信息是否完整，若完整则提示文件无误并打印文件内容
                    if(digestMessage.equals(rece_digestMessage)){
                        System.out.println("文件无误,接受的文件信息为：" + file_content);
                    }else{
                        System.out.println("摘要信息不完整，存在信息丢失！！！");
                    }
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //关闭资源
            this.ds.close();
        }
    }

    /**
     * 定义一个获取随机验证码的方法：getCode();
     * @param n
     * @return
     */
    public static String getCode(int n) {
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
        StringBuffer sb = new StringBuffer(); 	//声明一个StringBuffer对象sb 保存 验证码
        for (int i = 0; i < n; i++) {
            Random random = new Random();		//创建一个新的随机数生成器
            int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
            char ch = string.charAt(index);		//charAt() : 返回指定索引处的 char 值   -->赋值给char字符对象ch
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args){
        // TODO Auto-generated method stub
        HostA HostA = new HostA();
        System.out.println("这里是主机A：");
        psd = getCode(16);
        try {
            //初始化加密后的随机密码
            String message = encryptByPublicKey(psd,public_key);
            /*while(true) {*/
                datagramSocket = new DatagramSocket(port2);			//此处定义的为接收端口
                MyThread t = HostA.new MyThread(datagramSocket,message);
                t.start();
         /*       //如果上一个线程没有结束，则不继续生成线程,如果不进行等待，则无法继续绑定尚未释放的端口
                while(t.isAlive()) {
                    //设置休眠时间
                    Thread.sleep(1000);
                }
            }*/

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
