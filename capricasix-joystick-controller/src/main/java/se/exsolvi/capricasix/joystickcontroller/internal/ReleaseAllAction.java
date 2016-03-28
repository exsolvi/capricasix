package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public class ReleaseAllAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(ReleaseAllAction.class);

  @Override
  public void act(JoystickState state) {
    if (state.isButtonLeftArrow() && state.isButtonRightArrow()) {
      ServoController servoController;
      try {
        servoController = servoControllerServiceTracker.waitForService(5000);
        if (servoController != null) {
          servoController.releaseAll();
        }
      } catch (InterruptedException e) {
        // Ignore
      }
    }

  }

}
