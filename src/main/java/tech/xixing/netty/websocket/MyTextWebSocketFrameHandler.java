package tech.xixing.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/12 9:45 AM
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:"+ LocalDateTime.now()+"收到了:"+msg.text()));
        //ctx.writeAndFlush("服务器时间:"+ LocalDateTime.now()+"发送了:"+next);
        //scanner.close();
    }



    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * id表示唯一的值，LongText是唯一的，ShortText不是唯一的
         */
        System.out.println("handlerAdded被调用:"+ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用:"+ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //super.handlerRemoved(ctx);
        /**
         * id表示唯一的值，LongText是唯一的，ShortText不是唯一的
         */
        System.out.println("handlerRemoved被调用:"+ctx.channel().id().asLongText());
        System.out.println("handlerRemoved被调用:"+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        System.out.println("异常发生："+cause.getMessage());
        ctx.close();
    }
}
