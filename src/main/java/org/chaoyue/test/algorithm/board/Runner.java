package org.chaoyue.test.algorithm.board;

/**
 * Created by chaoyue on 2017/4/20.
 */
public class Runner {

  private float speed = 0;

  /**
   * 1: 向乙, -1: 向甲
   */
  private int direction = 1;

  /**
   * 甲地的偏移量
   */
  private float deltaDistance = 0;

  private float wholeDistance = 0;


  public Runner(float deltaDistance, int direction, float speed) {
    this.deltaDistance = deltaDistance;
    this.direction = direction;
    this.speed = speed;
  }

  /**
   * 时间流逝几秒
   */
  public void timeGo() {
    deltaDistance = deltaDistance + speed * direction;
    wholeDistance += speed;

  }

  public float getDeltaDistance() {
    return deltaDistance;
  }

  public int getDirection() {
    return direction;
  }

  public void turnback() {
    this.direction = this.direction * -1;
  }

  public float getWholeDistance() {
    return wholeDistance;
  }
}
