package tech.xixing.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/29 7:00 PM
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception{
        final InetSocketAddress socketAddress = new InetSocketAddress(6666);
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();

        final ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(socketAddress);
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        while (true){
            final SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (-1!=readCount){
                try{
                    readCount+=socketChannel.read(byteBuffer);
                }catch (Exception e){
                    e.printStackTrace();
                }
                byteBuffer.rewind();//倒带 position = 0 mark作废
            }
        }



    }
}
