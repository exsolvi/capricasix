package se.exsolvi.capricasix.trajectory.internal;

import java.util.concurrent.CompletableFuture;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.capricasix.trajectory.Trajectory;

public class TrajectoryPlanner implements Trajectory {

  private static final Logger log = LoggerFactory.getLogger(TrajectoryPlanner.class);
  private ServiceTracker<ServoController, ServoController> servoControllerServiceTracker;
  // private Logger log = LoggerFactory.getLogger(Trajectory.class);

  private int resolutionInDegrees = 5;
  private int stepTimeInMillisecond = 501;

  public TrajectoryPlanner() {
    BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
    servoControllerServiceTracker = new ServiceTracker<ServoController, ServoController>(context, ServoController.class, null);
    servoControllerServiceTracker.open();
  }

  public CompletableFuture<Void> move(int targetPosition) {

    CompletableFuture<Void> movement = CompletableFuture.runAsync(new Runnable() {

      @Override
      public void run() {
        ServoController servoController = servoControllerServiceTracker.getService();
        if (servoController == null) {
          return;
        } else {
          int startPosition = servoController.getActiveServo().getPositionInPercent();
          log.info("Start position : " + startPosition);
          log.info("Target position: " + targetPosition);
          int steps = Math.abs((startPosition - targetPosition) / resolutionInDegrees);
          log.info("Steps: " + steps);
          for (int i = 0; i < steps; i++) {
            if (startPosition < targetPosition) {
              log.info("Setting position to: " + (startPosition + i * resolutionInDegrees));
              servoController.getActiveServo().setPositionInPercent(startPosition + i * resolutionInDegrees);
            } else {
              log.info("Setting position to: " + (startPosition - i * resolutionInDegrees));
              servoController.getActiveServo().setPositionInPercent(startPosition - i * resolutionInDegrees);
            }
            try {
              Thread.sleep(stepTimeInMillisecond);
            } catch (InterruptedException e) {
              e.printStackTrace();
              return;
            }
          }
          servoController.getActiveServo().setPositionInPercent(targetPosition);
          log.info("End position: " + servoController.getActiveServo().getPositionInPercent());
        }
      }
    });
    return movement;

  }
}
