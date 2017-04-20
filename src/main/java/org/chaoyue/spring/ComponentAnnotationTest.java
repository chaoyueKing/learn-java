package org.chaoyue.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chaoyue on 2017/4/19.
 */
@Configuration
public class ComponentAnnotationTest {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
    annotationConfigApplicationContext.register(ComponentAnnotationTest.class);
    annotationConfigApplicationContext.refresh();
    InjectClass injectClass = annotationConfigApplicationContext.getBean(InjectClass.class);
    injectClass.print();

  }
  @MyComponent
  public static class InjectClass {
    public void print() {
      System.out.println("hello annotation !");
    }
  }
}
