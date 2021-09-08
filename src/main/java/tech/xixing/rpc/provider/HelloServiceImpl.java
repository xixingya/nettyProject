package tech.xixing.rpc.provider;

import tech.xixing.rpc.api.HeloService;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 9:39 AM
 */
public class HelloServiceImpl implements HeloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息:"+msg);
        return "服务器收到了你的消息:"+msg;
    }
}
