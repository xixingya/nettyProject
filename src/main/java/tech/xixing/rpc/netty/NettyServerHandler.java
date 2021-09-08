package tech.xixing.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import tech.xixing.rpc.provider.HelloServiceImpl;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 9:50 AM
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str =(String) msg;
        //获取客户端，发送的消息，并调用服务
        System.out.println("msg = "+str);
        /**
         * 客户端在调用服务器api的时候，我们需要定义一个协议
         * 比如我们要求，每次发送消息都必须以某个字符串开头 "HelloService#hello#msg"
         */
        if(((String) msg).startsWith("HelloService#hello#")){
            final String[] split = ((String) msg).split("#");
            StringBuilder sb=new StringBuilder();
            if(split.length>2){
                for(int i=2;i<split.length;i++){
                    sb.append(split[i]);
                }
            }
            final String hello = new HelloServiceImpl().hello(sb.toString());
            ctx.writeAndFlush(hello);
        }
        //super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
        //super.exceptionCaught(ctx, cause);
    }
}
