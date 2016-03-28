package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.joystickgw.JoystickState;

public class SpeedMeasurementAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(SpeedMeasurementAction.class);
  private boolean speedMeasurementActive = false;

  private SpeedMeasurementEngine speedMeasurementEngine = new SpeedMeasurementEngine(servoControllerServiceTracker);
  private Thread speedMeasurementEngineThread;

  @Override
  public void act(JoystickState state) {
    if (!speedMeasurementActive && state.isFire() && state.isButtonF2()) {
      log.info("Starting speed measurement");
      speedMeasurementActive = true;
      if (speedMeasurementEngine == null || !speedMeasurementEngine.isRunning()) {
        speedMeasurementEngineThread = new Thread(speedMeasurementEngine);
        speedMeasurementEngineThread.start();
      }
    }
    if (speedMeasurementActive && state.isFire() && state.isButton2()) {
      log.info("Stopping speed measurement");
      speedMeasurementActive = false;
      if (speedMeasurementEngine.isRunning()) {
        speedMeasurementEngine.stop();
      }
    }
    if (speedMeasurementActive) {
      long speed = Math.round((1-state.getZ())*500);
      speedMeasurementEngine.setDelay(speed);
      if(state.isButtonLeftArrow()) {
        speedMeasurementEngine.useCurrentSpeed();
      }
      
    }

  }
}
