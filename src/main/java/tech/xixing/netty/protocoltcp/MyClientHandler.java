package tech.xixing.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/19 7:25 PM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 hello,server 编号
        System.out.println("channel active client");

        String msg ="netty";
        for (int i = 0; i < 1000; i++) {
            final byte[] bytes = msg.getBytes();
            int length = msg.getBytes().length;
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setContent(bytes);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, Charset.forName("utf-8")));
        System.out.println("服务器接收消息数量=" + (++this.count));

    }
}
