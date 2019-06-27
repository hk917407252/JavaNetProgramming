package exer6_1_1_SysEncryption;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

/**
 * 简单的对称加密算法
 */
public class SimpleSysEnctyption {
    private static final Charset charset = Charset.forName("UTF-8");
    /**
     * 根据行类读取文章内容
     * @param path_name
     */
    public static String read_ByLine(String path_name) {
        FileReader reader = null;
        BufferedReader br = null;
        String content = "";
        try {
            String str = null;
            reader = new FileReader(path_name);
            br = new BufferedReader(reader);
            while((str = br.readLine())!= null) {
                content = content + str;
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
        return content;
    }

    /**
     * 按行写入文件,将加密后的文章写入文件中
     * @param path_name
     */
    public static void write_ByLine(String path_name,String content) {
        //声明文件写入以及写入缓存
        FileWriter writer = null;
        BufferedWriter bw = null;

        try {
            //判断文件是否存在
            File file = new File(path_name);
            if(!file.exists()) {
                file.createNewFile();
            }
            //定义以追加的方式写入字符串
            writer = new FileWriter(path_name,true);
            bw = new BufferedWriter(writer);

            //按行写入目标文件
            bw.newLine();
            bw.write(content);
            bw.close();
            writer.close();

        }catch (Exception e) {
            e.printStackTrace();
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
     * 加密算法
     * @param enc
     * @return
     */
    public static String encode(String enc,String key){
        byte[] b = enc.getBytes(charset);
        for(int i=0,size=b.length;i<size;i++){
            for(byte keyBytes0:key.getBytes()){
                b[i] = (byte) (b[i]^keyBytes0);
            }
        }
        return new String(b);
    }

    /**
     * 解密
     * @param dec
     * @return
     */
    public static String decode(String dec,String key){
        byte[] e = dec.getBytes(charset);
        byte[] dee = e;
        for(int i=0,size=e.length;i<size;i++){
            for(byte keyBytes0:key.getBytes()){
                e[i] = (byte) (dee[i]^keyBytes0);
            }
        }
        return new String(e);
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args)  {
        String source = "sourse.txt";
        String target = "privateArtcle.txt";

        //从文件中读取短文
        String content = read_ByLine((source));

        //根据用户指定的密码长度对文章进行加密
        System.out.println("请输入随机密码长度：");
        Scanner scanner = new Scanner(System.in);
        int psd_length = scanner.nextInt();
        String key =getCode(psd_length);

        System.out.println("随机密码为："+key);
        System.out.println("加密前："+content);
        //对数据进行加密
        String private_artcle = encode(content,key);
        System.out.println("加密后：" + private_artcle);
        String decode_artcle = decode(private_artcle,key);
        System.out.println("解密后：" +decode_artcle);
    }
}
