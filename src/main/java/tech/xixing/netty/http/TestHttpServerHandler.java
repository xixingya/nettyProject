package tech.xixing.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.Charset;

/**
 * 说明：
 *   1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 *   2.HttpObject   客户端和服务器端相互通信的数据被封装成HttpObject
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/6 9:49 AM
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //System.out.println("msg = "+msg);
        //判断msg是不是一个httpRequest请求
        if(msg instanceof HttpRequest) {
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址=" + ctx.channel().remoteAddress());

            System.out.println("pipeline hashcode:"+ ctx.pipeline().hashCode()+"  handler hashcode:"+this.hashCode());


            HttpRequest httpRequest = (HttpRequest) msg;

            URI uri = new URI(httpRequest.uri());

            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("request favicon.ico no response");
                return;

            }


            //回复给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello http嘿嘿嘿", CharsetUtil.UTF_8);

            //构造一个http的响应，即httpresonse
            final DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;Charset=UTF-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好的response返回
            ctx.writeAndFlush(httpResponse);

        }

    }
}
