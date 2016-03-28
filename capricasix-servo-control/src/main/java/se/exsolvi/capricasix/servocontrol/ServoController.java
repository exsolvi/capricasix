package se.exsolvi.capricasix.servocontrol;

public interface ServoController {

  public void releaseAll();

  public int getNumerOfServos();

  public Servo getServo(int servoNumer);

  public Servo getActiveServo();

  public Servo[] getAllServos();

  public void init(int numberOfServos);

  public void calibrate(int min, int max, int zero, int full);
  
  public void calibrate(int channel, int min, int max, int zero, int full);

  public void calibrateFullmovementDelay(int delayInMilliseconds);

  public void testServo(float testValue);

  public void testServo(int channel, float testValue);

  public void setChannel(int channel);

}
