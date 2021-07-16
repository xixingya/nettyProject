package tech.xixing.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 9:28 AM
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        final FileInputStream fileInputStream = new FileInputStream("src/main/resources/1.txt");
        final FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream=new FileOutputStream("src/main/resources/2.txt");
        final FileChannel outputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        while (true){
            byteBuffer.clear();
            final int read = fileInputStreamChannel.read(byteBuffer);
            if(read==-1){
                break;
            }
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();


    }
}
