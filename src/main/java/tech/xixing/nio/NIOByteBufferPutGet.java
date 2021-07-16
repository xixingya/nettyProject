package tech.xixing.nio;

import java.nio.ByteBuffer;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/11 10:02 AM
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(600);
        //类型放入buffer
        byteBuffer.putInt(10);
        byteBuffer.putLong(9L);
        byteBuffer.putChar('a');
        byteBuffer.putShort((short) 1);

        byteBuffer.flip();
        //final byte b = byteBuffer.get();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());

    }
}
