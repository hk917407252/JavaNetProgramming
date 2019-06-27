package exer5_3_1Reptile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reptile {

    // 地址
	/* private static final String URL = "http://localhost:8080/Exam05/exer5_1_1_TelnetHttp/personalGradeSearch.jsp";*/
    private static final String URL = "http://www.tooopen.com/view/1439719.html";
    
    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";
    
    //声明文件写入以及写入缓存
  	static FileWriter writer = null;
  	static BufferedWriter bw = null;
  	
  	//声明文件路径
  	static String path_name = "reptile_repository.txt";
    
    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        try {
        	//判断文件是否存在
			File file = new File(path_name);
			if(!file.exists()) {
				file.createNewFile();
			}
			//定义以追加的方式写入字符串
			writer = new FileWriter(path_name,true);
			bw = new BufferedWriter(writer);
        	
        	Reptile r=new Reptile();
            //获得html文本内容
            String HTML = r.getHtml(URL);
            //获取图片标签
            List<String> imgUrl = r.getImageUrl(HTML);
            //获取图片src地址
            List<String> imgSrc = r.getImageSrc(imgUrl);
            
            //将图片信息写入文件
            r.writeImgUrlSrc(imgUrl,imgSrc);
            
           /* //下载图片
            r.Download(imgSrc);*/
            bw.close();
            writer.close();
        }catch (Exception e){
            System.out.println("发生错误");
        }
    }
    
    /**
     * 将图片信息写入到文件中
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
    * 获取HTML内容
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
            //按行写入目标文件
			bw.newLine();
			bw.write(line);
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    /**
     * 获取ImageUrl地址
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
     * 获取ImageSrc地址
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