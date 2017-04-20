package org.chaoyue.test.algorithm.board;

/**
 * Created by chaoyue on 2017/4/20.
 */
public class BoardTest {

  private static int juli = 120;

  private static double jia = 0;

  private static double yi = 0;

  private static double bird = 0;


  static class jia implements Runnable {
    public void run() {
      int i = 1;
      while (true) {
        jia = (2.5 * i);
        System.out.println("甲跑了" + jia);
        if ((120 - (jia + yi)) < 0) {
          break;
        }
        i++;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class yi implements Runnable {
    public void run() {
      int i = 1;
      while (true) {
        yi = (3.33 * i);
        System.out.println("乙跑了" + yi);
        if ((120 - (jia + yi)) < 0) {
          break;
        }
        i++;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class bird implements Runnable {
    public void run() {
      int i = 1;
      while (true) {
        bird = (6.66 * i);
        System.out.println("鸟儿飞了" + bird);
        if ((120 - (jia + yi)) < 0) {
          break;
        }
        i++;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
  public static void main(String[] args) {
    Thread jia = new Thread(new jia());
    Thread yi = new Thread(new yi());
    Thread bird = new Thread(new bird());

    jia.start();
    yi.start();
    bird.start();
  }
}
