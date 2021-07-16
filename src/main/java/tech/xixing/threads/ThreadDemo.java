package tech.xixing.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/24 7:13 PM
 */
public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<String,Object> map=new HashMap<>();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Thread thread = new Thread(()-> {
            System.out.println(1);
        });
        thread.start();
        changeValue(atomicBoolean);
        System.out.println(atomicBoolean.get());


    }

    public static void changeValue(AtomicBoolean atomicBoolean){
        atomicBoolean.set(true);
    }
}
