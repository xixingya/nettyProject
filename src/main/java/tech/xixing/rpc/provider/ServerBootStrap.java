package tech.xixing.rpc.provider;

import tech.xixing.rpc.netty.NettyServer;

/**
 * 启动一个服务提供者
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 9:43 AM
 */
public class ServerBootStrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",1234);
    }
}
