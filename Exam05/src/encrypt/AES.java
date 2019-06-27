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
			//��ȡһ����Կ������
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			//��ʼ����Կ������
			generator.init(128);
			
			//������Կ
			SecretKey skey = generator.generateKey();
			
			//��ȡһ��AES��������
			Cipher cipher = Cipher.getInstance("AES");
			//��������������Ϊ����ģʽ��ͬʱ����Կ��������
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			
			//��������Ҫ���ܵ�����
			byte[] encrited= cipher.doFinal("explicit".getBytes());
			//�����ܺ�����ݽ���Ϊ�ַ���
			String s1 = new String(encrited);
			System.out.println("���ܺ�"+s1);
			
			//��������������Ϊ����
			cipher.init(Cipher.DECRYPT_MODE,skey);
			//���ܣ�
			byte[] decrited = cipher.doFinal(encrited);
			String s2 = new String(decrited);
			System.out.println("���ܺ�"+s2);
			
			
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
