package tech.xixing.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/6 9:48 AM
 */
public class TestServer {



    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 配置参数，服务端启动对象
         */
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        try{

            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInit());
            //bossGroup-> handler workerGroup ->childHandler

            final ChannelFuture channelFuture = serverBootstrap.bind(1234).sync();
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        } finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }
}
