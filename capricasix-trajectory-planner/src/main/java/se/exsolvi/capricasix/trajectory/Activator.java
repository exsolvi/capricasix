package se.exsolvi.capricasix.trajectory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import se.exsolvi.capricasix.trajectory.internal.AdafruitPCA9685;

public class Activator implements BundleActivator, ServiceListener {
  
  private AdafruitPCA9685 controller;

  public void start(BundleContext context) {
    System.out.println("Spider: Starting");
    controller = new AdafruitPCA9685();
    controller.setPWMFreq(100);
    controller.setPWM(4, 0, 0);
    context.addServiceListener(this);
    controller.setServoPulse(1, 1.75f);
  }

  public void stop(BundleContext context) {
    controller.setPWM(4, 0, 0);
    context.removeServiceListener(this);
    System.out.println("Spider: Stopped.");
  }

  public void serviceChanged(ServiceEvent event) {
    String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");

    if (event.getType() == ServiceEvent.REGISTERED) {
      System.out.println("Ex1: Service of type " + objectClass[0] + " registered.");
    } else if (event.getType() == ServiceEvent.UNREGISTERING) {
      System.out.println("Ex1: Service of type " + objectClass[0] + " unregistered.");
    } else if (event.getType() == ServiceEvent.MODIFIED) {
      System.out.println("Ex1: Service of type " + objectClass[0] + " modified.");
    }
  }

}