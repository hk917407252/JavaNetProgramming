package exer5_3_1Reptile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reptile {

    // ��ַ
	/* private static final String URL = "http://localhost:8080/Exam05/exer5_1_1_TelnetHttp/personalGradeSearch.jsp";*/
    private static final String URL = "http://www.tooopen.com/view/1439719.html";
    
    // ��ȡimg��ǩ����
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // ��ȡsrc·��������
    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";
    
    //�����ļ�д���Լ�д�뻺��
  	static FileWriter writer = null;
  	static BufferedWriter bw = null;
  	
  	//�����ļ�·��
  	static String path_name = "reptile_repository.txt";
    
    /**
     * ������
     * @param args
     */
    public static void main(String[] args) {
        try {
        	//�ж��ļ��Ƿ����
			File file = new File(path_name);
			if(!file.exists()) {
				file.createNewFile();
			}
			//������׷�ӵķ�ʽд���ַ���
			writer = new FileWriter(path_name,true);
			bw = new BufferedWriter(writer);
        	
        	Reptile r=new Reptile();
            //���html�ı�����
            String HTML = r.getHtml(URL);
            //��ȡͼƬ��ǩ
            List<String> imgUrl = r.getImageUrl(HTML);
            //��ȡͼƬsrc��ַ
            List<String> imgSrc = r.getImageSrc(imgUrl);
            
            //��ͼƬ��Ϣд���ļ�
            r.writeImgUrlSrc(imgUrl,imgSrc);
            
           /* //����ͼƬ
            r.Download(imgSrc);*/
            bw.close();
            writer.close();
        }catch (Exception e){
            System.out.println("��������");
        }
    }
    
    /**
     * ��ͼƬ��Ϣд�뵽�ļ���
     * @param imgUrl
     * @param imgSrc
     */
    private void writeImgUrlSrc(List<String> imgUrl,List<String> imgSrc) {
    	
    	try {
    		for(String url:imgUrl) {
    			bw.newLine();
    			bw.write(url);
        	}
    		
    		for(String src:imgSrc) {
    			bw.newLine();
    			bw.write(src);
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    /**
    * ��ȡHTML����
    * @param url
    * @return
    * @throws Exception
    */
    private String getHtml(String url)throws Exception{
        URL url1=new URL(url);
        URLConnection connection=url1.openConnection();
        InputStream in=connection.getInputStream();
        InputStreamReader isr=new InputStreamReader(in);
        BufferedReader br=new BufferedReader(isr);

        String line;
        StringBuffer sb=new StringBuffer();
        while((line=br.readLine())!=null){
            sb.append(line,0,line.length());
            sb.append('\n');
            //����д��Ŀ���ļ�
			bw.newLine();
			bw.write(line);
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    /**
     * ��ȡImageUrl��ַ
     * @param html
     * @return
     */
    private List<String> getImageUrl(String html){
        Matcher matcher=Pattern.compile(IMGURL_REG).matcher(html);
        List<String>listimgurl=new ArrayList<String>();
        while (matcher.find()){
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }

    /**
     * ��ȡImageSrc��ַ
     * @param listimageurl
     * @return
     */
    private List<String> getImageSrc(List<String> listimageurl){
        List<String> listImageSrc=new ArrayList<String>();
        for (String image:listimageurl){
            Matcher matcher=Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()){
                listImageSrc.add(matcher.group().substring(0, matcher.group().length()-1));
            }
        }
        return listImageSrc;
    }

    
}