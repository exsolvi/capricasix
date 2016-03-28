package se.exsolvi.capricasix.joystickcontroller.internal;

import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;

public class SpeedMeasurementEngine implements Runnable {

  protected Logger log = LoggerFactory.getLogger(JogAction.class);
  private ServiceTracker<ServoController, ServoController> servoControllerServiceTracker;

  private boolean running = false;
  private int delayInMilliseconds;
  private boolean setCurrentSpeed;

  public SpeedMeasurementEngine(ServiceTracker<ServoController, ServoController> servoControllerServiceTracker) {
    this.servoControllerServiceTracker = servoControllerServiceTracker;
  }

  public void setDelay(long delayInMilliseconds) {
    this.delayInMilliseconds = (int) delayInMilliseconds;
  }

  @Override
  public void run() {
    running = true;
    try {
      ServoController servoController = servoControllerServiceTracker.waitForService(5000);
      while (running) {
        if (servoController != null) {
          servoController.getActiveServo().setMax();
          Thread.sleep(delayInMilliseconds);
          servoController.getActiveServo().setMin();
          Thread.sleep(delayInMilliseconds);
          log.debug("Delay: {}", delayInMilliseconds);
          if (setCurrentSpeed) {
            servoController.calibrateFullmovementDelay(delayInMilliseconds);
          }
        } else {
          log.warn("Servo controller not available");
        }
      }
    } catch (InterruptedException e) {
      // Ignore
    }
  }

  public void stop() {
    this.running = false;
  }

  public boolean isRunning() {
    return running;
  }

  public void useCurrentSpeed() {
    this.setCurrentSpeed = true;
  }

}
