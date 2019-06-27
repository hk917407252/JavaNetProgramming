package exam03_3_1_4_getServerTime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatimeServer {
	private static final int PORT = 13;// 端口
    private final static int THREAD_COUNT = 50;// 线程数量

    public static void main(String[] args) {
    	//定义线程池
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                // 嵌套try块是为了区分两类异常
                try {
                    Socket connection = server.accept();
                    DaytimeTask task = new DaytimeTask(connection);
                    pool.submit(task);
                }catch(IOException e){

                }
            }
        } catch (IOException e) {
            System.err.println("启动服务器失败！！");
        }
    }
}

//定义Daytime任务类
class DaytimeTask implements Callable<Void> {
    private Socket connection;
    DaytimeTask(Socket connection) {
        this.connection = connection;
    }
    
    @Override
    public Void call(){
        try (Writer out = new OutputStreamWriter(connection.getOutputStream());) {
                Date nowadays = new Date();
                out.write(nowadays.toString());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
}
