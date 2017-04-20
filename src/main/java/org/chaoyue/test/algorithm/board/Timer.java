package org.chaoyue.test.algorithm.board;

/**
 * Created by chaoyue on 2017/4/20.
 */
public class Timer implements Runnable{

  private Runner trainA;
  private Runner trainB;
  private Runner bird;
  private KnockCalculater calc;
  private int time = 0;
  private int timeDelta = 1;

  public Timer(Runner trainA, Runner trainB, Runner bird, KnockCalculater calc) {
    this.trainA = trainA;
    this.trainB = trainB;
    this.bird = bird;
    this.calc = calc;
  }
  public void run() {
    // 初始状态
    printRunnerStatus();

    while (!Thread.interrupted()) {

      try {
        Thread.sleep(1000 * timeDelta);
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.exit(-1);
      }

      time = time + timeDelta;

      trainA.timeGo();
      trainB.timeGo();
      bird.timeGo();
      printRunnerStatus();

      calc.timeGo();


    }

  }

  private void printRunnerStatus() {
    StringBuffer sb = new StringBuffer();
    sb.append("").append(time).append(" min ");
    sb.append("[火车A:距甲").append(trainA.getDeltaDistance()).append("公里:方向").append(trainA.getDirection() == 1 ? "乙" : "甲").append("] ");
    sb.append("[火车B:距甲:").append(trainB.getDeltaDistance()).append("公里:方向").append(trainB.getDirection() == 1 ? "乙" : "甲").append("] ");
    sb.append("[小鸟:距甲").append(bird.getDeltaDistance()).append("公里:方向").append(bird.getDirection() == 1 ? "乙" : "甲").append("] ");

    System.out.println(sb.toString());
  }

}
