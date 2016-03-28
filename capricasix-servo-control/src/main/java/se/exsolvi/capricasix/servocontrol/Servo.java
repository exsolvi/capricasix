package se.exsolvi.capricasix.servocontrol;


public interface Servo {

  public void setMin();

  public void setMax();

  public void setPositionInPercent(float position);

  public void release();

  public void hold();

  public int getPositionInPercent();

}
