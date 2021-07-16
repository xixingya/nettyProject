package tech.xixing.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 10:13 AM
 *
 * 1.MappedByteBuffer可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MapperedBufferTest {

    public static void main(String[] args) throws Exception {
        final RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/1.txt", "rw");
        //获取对应的通道
        final FileChannel channel = randomAccessFile.getChannel();
        /**
         * map
         * 1. FileChannel.MapMode.READ_WRITE使用的是读写模式
         * 2. 0代表可以修改的起始位置
         * 3. 5代表映射到内存的大小，即将1.txt的多少个字节映射内存中
         * 实现类为DirectByteBuffer
         */
        final MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(4,(byte)'L');
        randomAccessFile.close();


    }
}
