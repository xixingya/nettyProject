package tech.xixing.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 10:31 AM
 *
 * Scattering:将数据写入到buffer时，可以采用buffer数组，依次写入【分散】
 * Gathering:从buffer读数据的时候，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {

        //使用ServerSocketChannel
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer[] byteBuffers=new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);

        //得到socketChannel
        final SocketChannel socketChannel = serverSocketChannel.accept();
        int maxSocketLength=8;
        while (true){

            int byteRead=0;
            while (byteRead<maxSocketLength){
                final long read = socketChannel.read(byteBuffers);
                byteRead+=read;
                System.out.println("累计读了"+byteRead+"个");
                //流打印，看看当前的这个buffer的position和limit
                Arrays.asList(byteBuffers).stream().
                        map(byteBuffer -> "当前的buffer数据 ，position="+
                                byteBuffer.position()+"limit="+ byteBuffer.limit()).
                        forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
            long byteWrite = 0;
            while (byteWrite<maxSocketLength){
                final long write = socketChannel.write(byteBuffers);
                byteWrite+=write;
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead= "+byteRead+", byteWrite = "+byteWrite);
        }




    }
}
