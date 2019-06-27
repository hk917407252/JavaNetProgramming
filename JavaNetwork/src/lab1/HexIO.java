package lab1;

import java.io.FileInputStream;
import java.io.IOException;

public class HexIO {
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream( "Test.txt" );
			int b,n=0;
			while ((b=fis.read())!=-1){
				System.out.print(" "+Integer.toHexString(b));
				//控制每行的元素个数为10
				if (((++n)%10)==0) System.out.println();
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
