package tech.xixing.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/9 7:46 PM
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个channel，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private volatile SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //super.handlerAdded(ctx);
        final Channel channel = ctx.channel();
        /**
         * 将该客户加入聊天得信息推送给其他在线客户端;
         *
         */
        channelGroup.writeAndFlush("[客户端]"+ channel.remoteAddress()+"加入聊天\n");
        channelGroup.add(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //获取到channel
        final Channel channel = ctx.channel();
        //遍历
        channelGroup.forEach(ch->{
            if(ch!=channel){
                ch.writeAndFlush("[客户端]"+channel.remoteAddress()+"在"+simpleDateFormat.format(new Date())+"说："+msg);
            }else {
                ch.writeAndFlush("[自己]发送了一个消息:"+msg+"\n");
            }
        });

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了～\n");
        //super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了～\n");

        //super.channelInactive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //super.handlerRemoved(ctx);
        channelGroup.writeAndFlush("[客户端]"+ ctx.channel().remoteAddress()+"离开聊天\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }
}
