package org.chaoyue.spring;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by chaoyue on 2017/4/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {
  String value() default "";
}
