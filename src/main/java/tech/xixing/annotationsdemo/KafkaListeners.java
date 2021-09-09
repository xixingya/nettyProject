package tech.xixing.annotationsdemo;

import java.lang.annotation.*;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/9/9 11:50 AM
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface KafkaListeners {
    KafkaListener[] value();
}
