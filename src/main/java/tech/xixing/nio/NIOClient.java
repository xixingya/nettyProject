package tech.xixing.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/17 9:44 AM
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        if(!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("socket connect need time,the thread will not blocking");
            }
        }

        String msg = "hello nio";
        final ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();

//        Scanner scanner=new Scanner(System.in);
//        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
//        while (scanner.hasNext()){
//            byteBuffer.clear();
//            byteBuffer.put(scanner.next().getBytes());
//            socketChannel.write(byteBuffer);
//        }


    }
}
