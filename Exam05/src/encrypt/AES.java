package encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AES {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//获取一个密钥生成器
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			//初始化密钥生成器
			generator.init(128);
			
			//生成密钥
			SecretKey skey = generator.generateKey();
			
			//获取一个AES加密密室
			Cipher cipher = Cipher.getInstance("AES");
			//将加密密室设置为加密模式，同时将密钥授予密室
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			
			//加密所需要加密的内容
			byte[] encrited= cipher.doFinal("explicit".getBytes());
			//将加密后的数据解析为字符串
			String s1 = new String(encrited);
			System.out.println("加密后："+s1);
			
			//将加密密室设置为解密
			cipher.init(Cipher.DECRYPT_MODE,skey);
			//解密：
			byte[] decrited = cipher.doFinal(encrited);
			String s2 = new String(decrited);
			System.out.println("解密后："+s2);
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
