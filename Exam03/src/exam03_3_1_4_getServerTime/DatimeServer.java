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
	private static final int PORT = 13;// �˿�
    private final static int THREAD_COUNT = 50;// �߳�����

    public static void main(String[] args) {
    	//�����̳߳�
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                // Ƕ��try����Ϊ�����������쳣
                try {
                    Socket connection = server.accept();
                    DaytimeTask task = new DaytimeTask(connection);
                    pool.submit(task);
                }catch(IOException e){

                }
            }
        } catch (IOException e) {
            System.err.println("����������ʧ�ܣ���");
        }
    }
}

//����Daytime������
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
