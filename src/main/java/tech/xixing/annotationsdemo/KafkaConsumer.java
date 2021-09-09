package tech.xixing.annotationsdemo;

import java.lang.annotation.Annotation;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/9/9 11:47 AM
 */
public class KafkaConsumer {

    @KafkaListener(namespace = "aaa")
    @KafkaListener(namespace = "bb")
    public void consumer(){

    }

    public static void main(String[] args) throws NoSuchMethodException {
        final Annotation[] consumers = KafkaConsumer.class.getMethod("consumer").getDeclaredAnnotations();

        System.out.println(consumers);
    }
}
