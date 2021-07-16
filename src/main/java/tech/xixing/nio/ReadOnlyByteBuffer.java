package tech.xixing.nio;

import java.nio.ByteBuffer;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 10:09 AM
 */
public class ReadOnlyByteBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i );
        }
        byteBuffer.flip();
        final ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        //读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        //写数据报错，不让写
        readOnlyBuffer.put((byte) 100);
    }
}
