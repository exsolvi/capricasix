package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public class JogAllUpperAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(JogAllUpperAction.class);

  private boolean jogging = false;

  @Override
  public void act(JoystickState state) {
    if (!jogging && state.isFire() && state.isButton5()) {
      log.info("Starting jogging");
      jogging = true;
    }
    if (jogging && state.isFire() && state.isButton2()) {
      log.info("Stopping jogging");
      jogging = false;
    }
    if (jogging) {
      try {
        ServoController servoController = servoControllerServiceTracker.waitForService(5000);
        if (servoController != null) {
          int pos = (int) Math.round(90 + state.getJoystick().getX() * 90);
          servoController.getServo(1).setPositionInDegrees(pos);
          servoController.getServo(3).setPositionInDegrees(pos);
          servoController.getServo(5).setPositionInDegrees(pos);
          servoController.getServo(7).setPositionInDegrees(pos);
          log.debug("Deg: {}", pos);
        } else {
          log.warn("Servo controller not available");
        }
      } catch (InterruptedException e) {
        // Ignore
      }

    }

  }

}
