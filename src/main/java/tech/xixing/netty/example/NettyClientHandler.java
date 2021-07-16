package tech.xixing.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/4 10:30 AM
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 当通道就绪就会触发
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client ChannelHandlerContext "+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server~", CharsetUtil.UTF_8));
        //super.channelActive(ctx);
    }

    /**
     *
     * @param ctx
     * @param msg 读取数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf=(ByteBuf) msg;
        System.out.println("from server get msg="+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("server remote address="+ctx.channel().remoteAddress());
        //super.channelRead(ctx, msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        //super.exceptionCaught(ctx, cause);
    }
}
