package se.exsolvi.capricasix.servocontrol.internal;

import se.exsolvi.capricasix.servocontrol.Servo;

public interface AdjustableServo extends Servo {

  public void setFullMovementDelay(int delayInMilliseconds);

  public void setMaxParameter(int min);

  public void setMinParameter(int min);

  public void setPosition(int position);

  public ServoParameters getServoParameters();

}
