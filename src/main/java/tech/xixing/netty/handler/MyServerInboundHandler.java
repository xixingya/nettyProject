package tech.xixing.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:48 AM
 */
public class MyServerInboundHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("from client"+ctx.channel().remoteAddress()+" read long:"+msg);
        //给客户端回送个msg
        ctx.writeAndFlush(5678L);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }
}
