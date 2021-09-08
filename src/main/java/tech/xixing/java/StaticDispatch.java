package tech.xixing.java;

import tech.xixing.java.bean.HumanPublic;
import tech.xixing.java.bean.ManPublic;
import tech.xixing.java.bean.WomanPublic;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/9/2 7:50 PM
 */
public class StaticDispatch {

    static abstract class Human{

    }

    static class Man extends Human{

    }

    static class Woman extends Human{

    }

    public  void sayHello(HumanPublic guy){
        System.out.println("hello guys!");
    }

    public  void sayHello(ManPublic guy){
        System.out.println("hello man!");
    }
    public  void sayHello(WomanPublic guy){
        System.out.println("hello lady!");
    }

    public static void main(String[] args) {

        HumanPublic woman =new WomanPublic();
        HumanPublic man =new ManPublic();
        //Human man=new Man();
        //Human woman = new Woman();
        //动态分配
        man.say();
        woman.say();

        System.out.println("--------------------");

        //静态分配
        final StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(woman);

    }
}
