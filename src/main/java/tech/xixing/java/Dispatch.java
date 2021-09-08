package tech.xixing.java;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/9/8 7:24 PM
 */
public class Dispatch {
    static class QQ{}

    static class _360{}

    public static class Father{
        public void hardChoice(QQ args){
            System.out.println("father choose qq");
        }
        public void hardChoice(_360 args){
            System.out.println("father choose 360");
        }
    }

    public static class Son extends Father{
        @Override
        public void hardChoice(QQ args){
            System.out.println("son choose qq");
        }
        @Override
        public void hardChoice(_360 args){
            System.out.println("son choose 360");
        }
    }

    public static void main(String[] args) {
        Father father =new Father();
        Father son =new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }


}
