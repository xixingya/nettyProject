package tech.xixing.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/29 7:07 PM
 */
public class NewIOClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel=SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost",6666));
        String filename = "src/main/resources/Mac.zip";
        final FileChannel fileChannel = new FileInputStream(filename).getChannel();
        final long startTime= System.currentTimeMillis();

        //在linux一个transferTo方法就可以完成传输
        //在windows下一次调用只能发送8MB，需要分段传输文件，而且要记录传输时的位置
        final long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("send bytes:"+transferCount+" use time:"+(System.currentTimeMillis()-startTime));
    }
}
