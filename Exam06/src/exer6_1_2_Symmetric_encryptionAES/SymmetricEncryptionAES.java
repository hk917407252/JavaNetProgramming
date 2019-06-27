package exer6_1_2_Symmetric_encryptionAES;

import java.io.*;
import java.security.Key;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 * 实现AES加密
 */
public class SymmetricEncryptionAES {
    /** 算法名称 */
    private static final String KEY_ALGORITHM = "AES";
    /** 算法名称/加密模式/填充方式 */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 按照所给的密码，生成一个固定密钥
     *
     * @param password 长度必须是16
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Key toKey(String password) throws UnsupportedEncodingException {
        return new SecretKeySpec(password.getBytes("UTF-8"), KEY_ALGORITHM);
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
     * 主函数
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String source = "sourse.txt";
        String target = "privateArtcle.txt";
        //从文件中读取短文
        String content = read_ByLine((source));

        //获取指定长度的随机加密密码
        String psd = getCode(16);
        System.out.println("加密密码为：" + psd);
        // 加密
        System.out.println("加密前：" + content);
        String encryptResultStr = encrypt(content, psd);
        System.out.println("加密后：" + encryptResultStr);

        //将加密后的内容写入文件中
        write_ByLine(target,encryptResultStr);
        // 解密
        System.out.println("解密后：" + decrypt(encryptResultStr, psd));



    }
}
