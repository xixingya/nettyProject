package tech.xixing.rpc.customer;

import tech.xixing.rpc.api.HeloService;
import tech.xixing.rpc.netty.NettyClient;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 7:41 PM
 */
public class ClientBootStrap {

    public static final String providerName = "HelloService#hello#msg";
    public static void main(String[] args) throws Exception{
        //创建一个消费者
        final NettyClient nettyClient = new NettyClient();
        for(;;){
            Thread.sleep(1000);
            //创建代理对象
            final HeloService heloService =(HeloService) nettyClient.getBean(HeloService.class, providerName);
            final String qaq = heloService.hello("qaq");
            System.out.println(qaq);

        }


    }
}
