package tech.xixing.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/9 7:04 PM
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        //创建ByteBuf
        //不需要flip，底层维护了readerIndex和writerIndex
        ByteBuf byteBuf= Unpooled.buffer(10);

        for(int i=0;i<10;i++){
            byteBuf.writeByte(i);
        }

        System.out.println("capacity="+byteBuf.capacity());
        //这个不会导致readerIndex的变化
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.getByte(i));
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
    }
}
