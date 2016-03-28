package se.exsolvi.capricasix.joystickcontroller.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public abstract class AbstractJoystickAction {

  protected ServiceTracker<ServoController, ServoController> servoControllerServiceTracker;

  public abstract void act(JoystickState state);

  public AbstractJoystickAction() {
    BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
    servoControllerServiceTracker = new ServiceTracker<ServoController, ServoController>(context, ServoController.class, null);
    servoControllerServiceTracker.open();
  }

}
