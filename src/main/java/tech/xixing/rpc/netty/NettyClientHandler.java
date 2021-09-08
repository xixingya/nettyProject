package tech.xixing.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 7:06 PM
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext channelHandlerContext; //上下文
    private String result;//返回的结果
    private String param;//传入的参数


    /**
     * 被代理对象调用，发送数据给服务器-> wait 等待被唤醒(channelRead)->返回结果、
     * （第三个）（第五个）
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        channelHandlerContext.writeAndFlush(param);
        //进行wait
        wait();//等待channelRead唤醒我，释放锁
        return result;
    }

    /**
     * 第一个被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive被调用");
        channelHandlerContext=ctx;
    }

    /**
     * 收到服务器的数据后调用方法（第四个）
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用");
        result=msg.toString();
        notify();//唤醒等待的线程，即call
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 第二个被调用
     * @param param
     */
    public void setParam(String param) {
        System.out.println("setParam 被调用");
        this.param = param;
    }
}
