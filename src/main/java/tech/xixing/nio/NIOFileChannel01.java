package tech.xixing.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/7 11:23 AM
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str="hello nio";
        //创建一个输出流
        final FileOutputStream fout = new FileOutputStream("/data/output/file01.txt");

        //通过输出流获得channel
        final FileChannel channel = fout.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fout.close();
        FileInputStream fileInputStream=new FileInputStream("/data/output/file01.txt");
        final FileChannel channel1 = fileInputStream.getChannel();
        ByteBuffer byteBuffer1=ByteBuffer.allocate(1024);
        channel1.read(byteBuffer1);
        String str2=new String(byteBuffer1.array(),0,byteBuffer1.position());
        System.out.println(str2);




    }
}
