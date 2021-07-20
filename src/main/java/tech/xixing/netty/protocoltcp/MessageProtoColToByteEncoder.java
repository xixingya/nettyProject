package tech.xixing.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/19 7:41 PM
 */
public class MessageProtoColToByteEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageProtoColToByteEncoder 被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
