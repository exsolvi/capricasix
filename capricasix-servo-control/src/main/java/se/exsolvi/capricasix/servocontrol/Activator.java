package se.exsolvi.capricasix.servocontrol;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.internal.controller.AdaFruitServoController;

public class Activator implements BundleActivator, ServiceListener {

  private Logger log = LoggerFactory.getLogger(Activator.class);
  private ServoController servocontroller;

  @Override
  public void start(BundleContext context) {
    log.info("Spider: Starting servo control");
    servocontroller = new AdaFruitServoController();
    servocontroller.init(8);
    context.registerService(ServoController.class, servocontroller, null);
    servocontroller.releaseAll();
    log.info("Spider: Started servo control");
  }

  @Override
  public void stop(BundleContext context) {
    servocontroller.releaseAll();
    log.info("Spider: Stopped servo control");
  }

  @Override
  public void serviceChanged(ServiceEvent event) {
  }

}