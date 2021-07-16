package tech.xixing.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/5/28 3:43 PM
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //1.创建一个线程池
        //2.客户端连接后创建一个线程
        final ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

        ServerSocket serverSocket=new ServerSocket(6666);
        System.out.println("服务端启动");

        while (true){
            //有客户端连接了
            final Socket socket = serverSocket.accept();
            System.out.println("有客户端连接！");
            pool.execute(()->{
                handler(socket);
            });

        }
    }

    public static void handler(Socket socket){
        try{
            byte[] bytes=new byte[1024];
            final InputStream inputStream = socket.getInputStream();
            while (true){
                final int read = inputStream.read(bytes);
                if(read!=-1){
                    System.out.println("current thread is "+Thread.currentThread().getName());
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){

        }finally {
            try{
                socket.close();
            }catch (Exception e){

            }

        }
    }
}
