package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public class CalibrationAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(CalibrationAction.class);

  private boolean calibrationActive = false;

  private float testValue = 0;

  private int zero;

  private int min;

  private int full;

  private int max;
  
  @Override
  public void act(JoystickState state) {
    if (!calibrationActive && state.isFire() && state.isButton3()) {
      log.info("Starting calbration");
      calibrationActive = true;
    }
    if (calibrationActive && state.isFire() && state.isButton2()) {
      log.info("Stopping calbration");
      calibrationActive = false;
    }
    if (calibrationActive) {
      ServoController servoController;
      try {
        servoController = servoControllerServiceTracker.waitForService(5000);
        if (servoController != null) {
          testValue = (float) (2-1.5*state.getZ());
          servoController.testServo(testValue);
          //log.debug("Value: {}", testValue);
          if (state.isButtonF2()) {
            this.min = Math.round(testValue*1000);
            log.debug("Min  : {}", this.min);
          }
          if (state.isButtonF1()) {
            this.zero = Math.round(testValue*1000);
            log.debug("Zero : {}", this.zero);
          }
          if (state.isButtonF4()) {
            this.full = Math.round(testValue*1000);
            log.debug("Full : {}", this.full);
          }
          if (state.isButtonF3()) {
            this.max = Math.round(testValue*1000);
            log.debug("Max : {}", this.max);
          }
          servoController.calibrate(min, max, zero, full);
        } else {
          log.warn("Servo controller not available");
        }
      } catch (InterruptedException e) {
        // Ignore
      }
      
    }

  }

}
