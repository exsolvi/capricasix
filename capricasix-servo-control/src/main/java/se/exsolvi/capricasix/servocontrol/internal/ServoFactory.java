package se.exsolvi.capricasix.servocontrol.internal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.internal.controller.AdafruitPCA9685;
import se.exsolvi.capricasix.servocontrol.internal.servos.ModelCraftRS2;

public class ServoFactory {

  private Logger log = LoggerFactory.getLogger(ServoFactory.class);
  private AdafruitPCA9685 hardwareController;

  public ServoFactory(AdafruitPCA9685 hardwareController) {
    this.hardwareController = hardwareController;
  }

  public AdjustableServo createModelCraftRS2(int channel) {
    ServoParameters servoParameters = readServoParameters(channel);
    ModelCraftRS2 servo = new ModelCraftRS2(servoParameters);
    servo.bind(hardwareController, channel);
    log.debug("Servo created on channel {}", channel);
    return servo;
  }

  private ServoParameters readServoParameters(int channel) {
    try (InputStream file = new FileInputStream("data/servo" + channel + ".data");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);) {
        ServoParameters servoParameters = (ServoParameters) input.readObject();
        return servoParameters;
    } catch (FileNotFoundException fnfe) {
      log.warn("No servo data found");
    } catch (ClassNotFoundException cnfe) {
      log.error("Class not found exception when reading servo data", cnfe);
    } catch (IOException ioe) {
      log.error("IOException when reading servo data", ioe);
    }
    return new ServoParameters();
  }

}
