package se.exsolvi.capricasix.joystickcontroller;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.joystickcontroller.internal.JoystickControllerImpl;

public class Activator implements BundleActivator, ServiceListener {

  private Logger log = LoggerFactory.getLogger(Activator.class);

  @Override
  public void start(BundleContext context) {
    log.info("Spider: Starting joystick controller");
    JoystickController joystickController = new JoystickControllerImpl();
    context.registerService(JoystickController.class, joystickController, null);
  }

  @Override
  public void stop(BundleContext context) {
    log.info("Spider: Stopped joystick controller.");
  }

  @Override
  public void serviceChanged(ServiceEvent event) {
  }

}