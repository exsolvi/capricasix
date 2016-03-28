package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public class JogAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(JogAction.class);

  private boolean jogging = false;
   
  @Override
  public void act(JoystickState state) {
    if (!jogging && state.isFire() && state.isButton4()) {
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
          servoController.getActiveServo().setPositionInDegrees((int) Math.round(90+state.getJoystick().getX()*90));
          log.debug("Deg: {}", (int) Math.round(90-state.getJoystick().getX()*90));
        } else {
          log.warn("Servo controller not available");
        }
      } catch (InterruptedException e) {
        // Ignore
      }
      
    }

  }

}
