package tech.xixing.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 9:46 AM
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception{
        final FileInputStream fileInputStream = new FileInputStream("src/main/resources/1.txt");
        final FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream=new FileOutputStream("src/main/resources/1_copy.txt");
        final FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        fileOutputStreamChannel.transferFrom(fileInputStreamChannel,0,fileInputStreamChannel.size());
        fileInputStream.close();
        fileOutputStream.close();

    }
}
