package tech.xixing.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:44 AM
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {


    /**
     *
     * 如果in里面还有数据，会一直调用这个方法
     * @param ctx 上下文
     * @param in 入栈的ByteBuf
     * @param out list集合，将解码后的数据传给下个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder 被调用");
        //因为long是八个字节
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
