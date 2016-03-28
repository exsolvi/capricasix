package se.exsolvi.capricasix.servocontrol.internal.servos;

import se.exsolvi.capricasix.servocontrol.internal.AdjustableServo;
import se.exsolvi.capricasix.servocontrol.internal.ServoParameters;
import se.exsolvi.capricasix.servocontrol.internal.controller.AdafruitPCA9685;

public class ModelCraftRS2 implements AdjustableServo {

  private int channel = -1;
  private AdafruitPCA9685 hardwareController = null;

  private ServoParameters servoParameters = new ServoParameters();
  private int position = 0;
  private ServoDriver servoDriver;
  private Thread servoDriverThread;

  public ModelCraftRS2(ServoParameters servoParameters) {
    this.servoParameters = servoParameters;
  }

  public void bind(AdafruitPCA9685 hardwareController, int channel) {
    this.hardwareController = hardwareController;
    this.channel = channel;
  }

  private void startServoDriver() {
    if (servoDriver == null || !servoDriver.isRunning()) {
      servoDriver = new ServoDriver();
      servoDriverThread = new Thread(servoDriver);
      servoDriverThread.start();
    }
  }

  @Override
  public void setMin() {
    setPosition(servoParameters.getMinimum());
  }

  @Override
  public void setMax() {
    setPosition(servoParameters.getMaximum());
  }

  @Override
  public void setPositionInPercent(float position) {
    this.position = Math.round((servoParameters.getMaximum() - servoParameters.getMinimum()) * (position / 100f));
    setPosition(this.position);
  }

  @Override
  public void release() {
    hardwareController.setPWM(channel, 0, 0);
    servoDriver.stop();
  }

  @Override
  public void hold() {
    setPosition(this.position);
  }

  @Override
  public int getPositionInPercent() {
    return Math.round(this.position * 100f / (servoParameters.getMaximum() - servoParameters.getMinimum()));
  }

  public void setPosition(int pulseInMicroSeconds) {
    startServoDriver();
    servoDriver.setValue(pulseInMicroSeconds);
  }

  @Override
  public void setMinParameter(int min) {
    this.servoParameters.setMinimum(min);
  }

  @Override
  public void setMaxParameter(int max) {
    this.servoParameters.setMaximum(max);
  }

  @Override
  public ServoParameters getServoParameters() {
    return new ServoParameters(servoParameters);
  }

  @Override
  public void setFullMovementDelay(int delayInMilliseconds) {
    this.servoParameters.setFullMovementDelay(delayInMilliseconds);

  }

  private class ServoDriver implements Runnable {

    private static final int frequency = 10;

    private boolean running = false;

    private int oldValue;
    private int newValue;

    @Override
    public void run() {
      this.running = true;
      while (running) {
        try {
          if (oldValue != newValue) {
            hardwareController.setServoPulse(channel, newValue / 1000f);
            oldValue = newValue;
            Thread.sleep(1000 / frequency);
          } else {
            Thread.sleep(100 / frequency);
          }

        } catch (InterruptedException e) {
          // Ignore
        }
      }
    }

    public boolean isRunning() {
      return running;
    }

    public void stop() {
      this.running = false;
    }

    public void setValue(int servoValue) {
      this.newValue = servoValue;
    }

  }

}
