package se.exsolvi.capricasix.servocontrol.internal.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.Servo;
import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.capricasix.servocontrol.internal.AdjustableServo;
import se.exsolvi.capricasix.servocontrol.internal.ServoFactory;

public class AdaFruitServoController implements ServoController {

  private final static Logger log = LoggerFactory.getLogger(AdaFruitServoController.class);

  private static final int CHANNEL_MAX = 15;

  private static final AdafruitPCA9685 hardwareController = new AdafruitPCA9685();
  private final ServoFactory servoFactory = new ServoFactory(hardwareController);

  private int numberOfServos;
  private AdjustableServo[] servos;

  private int channel;

  public AdaFruitServoController() {
    hardwareController.setPWMFreq(100);
  }

  @Override
  public void releaseAll() {
    for (int channel = 0; channel < CHANNEL_MAX; channel++) {
      hardwareController.setPWM(channel, 0, 0);
    }
  }

  @Override
  public Servo getServo(int servoNumer) {
    return servos[servoNumer];
  }

  @Override
  public int getNumerOfServos() {
    return numberOfServos;
  }

  @Override
  public Servo[] getAllServos() {
    // Return a copy to make it immutable.
    Servo[] copyOfServos = new Servo[numberOfServos];
    System.arraycopy(servos, 0, copyOfServos, 0, numberOfServos);
    return copyOfServos;
  }

  @Override
  public void init(int numberOfServos) {
    this.numberOfServos = numberOfServos;
    servos = new AdjustableServo[numberOfServos];
    for (int i = 0; i < numberOfServos; i++) {
      AdjustableServo servo = servoFactory.createModelCraftRS2(i);
      servos[i] = servo;
    }
  }

  @Override
  public void calibrate(int min, int max, int zero, int full) {
    calibrate(channel, min, max, zero, full);
  }

  @Override
  public void calibrate(int channel, int min, int max, int zero, int full) {
    servos[channel].setMinParameter(min);
    servos[channel].setMaxParameter(max);
    persistCalibrationData(channel);
  }

  private void persistCalibrationData(int channel) {
    try (OutputStream file = new FileOutputStream("data/servo" + channel + ".data");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);) {
      output.writeObject(servos[channel].getServoParameters());
    } catch (IOException ex) {
      log.error("Error storing servo data", ex);
    }
  }

  @Override
  public void testServo(float testValue) {
    testServo(channel, testValue);
  }

  @Override
  public void testServo(int channel, float value) {
    servos[channel].setPosition(Math.round(value * 1000));

  }

  @Override
  public void setChannel(int channel) {
    this.channel = channel;
  }

  @Override
  public Servo getActiveServo() {
    return servos[channel];
  }

  @Override
  public void calibrateFullmovementDelay(int delayInMilliseconds) {
    servos[channel].setFullMovementDelay(delayInMilliseconds);
    persistCalibrationData(channel);
  }
}
