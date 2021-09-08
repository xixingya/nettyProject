package tech.xixing.java;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/9/6 8:01 PM
 */
public class FieldHasNoPolymorphic {

    static class Father{
        public int money  =1;

        public Father(){
            money = 2;
            showMeTheMoney();
        }
        public void showMeTheMoney(){
            System.out.println("I am Father I have $"+money);
        }
    }

    static class Son extends Father{
        public Integer money =3;
        public Son(){
            money = 4;
            showMeTheMoney();
        }

        @Override
        public void showMeTheMoney(){
            System.out.println("I am Son I have $"+money);
        }
    }

    public static void main(String[] args) {
        Father gay =new Son();
        System.out.println("This human has $"+gay.money);
    }
}
