package exer6_1_3_AsymmetricEncryption;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 非对称加密算法RSA
 */
public class AsymmetricEncryptionRSA {
    //非对称密钥算法
    private static final String KEY_ALGORITHM = "RSA";
    //密钥长度
    private static final int KEY_SIZE = 512;
    //字符编码
    private static final String CHARSET = "UTF-8";

    /**
     * 生成密钥对
     *
     * @return KeyPair 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        return getKeyPair("HKs1h2Enw1ozXu20");
    }

    /**
     * 生成密钥对
     * @param password 生成密钥对的密码
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(String password) throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //根据设定的密钥大小,初始化密钥生成器
        if(password == null){
            keyPairGenerator.initialize(KEY_SIZE);
        }else {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(CHARSET));
            keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        }
        //生成密钥对
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 取得私钥
     *
     * @param keyPair 密钥对
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKeyBytes(KeyPair keyPair) {
        return keyPair.getPrivate().getEncoded();
    }

    /**
     * 取得Base64编码的私钥
     *
     * @param keyPair 密钥对
     * @return String Base64编码的私钥
     */
    public static String getPrivateKey(KeyPair keyPair) {
        return Base64.getEncoder().encodeToString(getPrivateKeyBytes(keyPair));
    }

    /**
     * 取得公钥
     *
     * @param keyPair 密钥对
     * @return byte[] 公钥
     */
    public static byte[] getPublicKeyBytes(KeyPair keyPair) {
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 取得Base64编码的公钥
     *
     * @param keyPair 密钥对
     * @return String Base64编码的公钥
     */
    public static String getPublicKey(KeyPair keyPair) {
        return Base64.getEncoder().encodeToString(getPublicKeyBytes(keyPair));
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥字节数组
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        //数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey Base64编码的私钥
     * @return String Base64编码的加密数据
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] key = Base64.getDecoder().decode(privateKey);
        return Base64.getEncoder().encodeToString(encryptByPrivateKey(data.getBytes(CHARSET), key));
    }

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
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(data), key), CHARSET);
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 公钥字节数组
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //产生公钥
        PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
        //数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data      Base64编码的待解密数据
     * @param publicKey Base64编码的公钥
     * @return String 解密数据
     */
    public static String decryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] key = Base64.getDecoder().decode(publicKey);
        return new String(decryptByPublicKey(Base64.getDecoder().decode(data), key), CHARSET);
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
            System.out.println("读取失败");
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
            System.out.println("写入失败");
        }
    }

    /**
     * 主函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //生成密钥对
        KeyPair keyPair = AsymmetricEncryptionRSA.getKeyPair();
        //获取公钥
        String publicKey = AsymmetricEncryptionRSA.getPublicKey(keyPair);
        //获取私钥
        String privateKey = AsymmetricEncryptionRSA.getPrivateKey(keyPair);

        //输出公钥、私钥信息
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);

        //定义文件路径
        String source = "sourse.txt";
        String target = "privateArtcle.txt";
        //从文件中读取短文
        String data = read_ByLine(source);
        {
            System.out.println("\n*************私钥加密，公钥解密*************");
            String s1 = AsymmetricEncryptionRSA.encryptByPrivateKey(data, privateKey);
            System.out.println("加密后的数据:" + s1);
            //将加密后的数据写入目标文件
            write_ByLine(target,s1);
            String s2 = AsymmetricEncryptionRSA.decryptByPublicKey(s1, publicKey);
            System.out.println("解密后的数据:" + s2 + "\n\n");
        }

        {
            System.out.println("\n*************公钥加密，私钥解密*************");
            String s1 = AsymmetricEncryptionRSA.encryptByPublicKey(data, publicKey);
            System.out.println("加密后的数据:" + s1);
            //将加密后的数据写入目标文件
            write_ByLine(target,s1);
            String s2 = AsymmetricEncryptionRSA.decryptByPrivateKey(s1, privateKey);
            System.out.println("解密后的数据:" + s2 + "\n\n");
        }

    }
}
