package org.chaoyue.test.algorithm.board;

/**
 * Created by chaoyue on 2017/4/20.
 */
public class Bootstrap {
  public static void main(String[] args) {
    Runner trainA = new Runner(0, 1, 150f / 60);
    Runner trainB = new Runner(120f, -1, 200f * 1 / 60);
    Runner bird = new Runner(0, 1, 400f / 60);

    KnockCalculater calc = new KnockCalculater(trainA,trainB,bird);

    new Thread(new Timer(trainA,trainB,bird,calc)).start();
  }
}
