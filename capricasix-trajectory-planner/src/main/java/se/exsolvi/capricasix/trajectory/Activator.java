package se.exsolvi.capricasix.trajectory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.trajectory.internal.TrajectoryPlanner;

public class Activator implements BundleActivator, ServiceListener {

  private Logger log = LoggerFactory.getLogger(Activator.class);

  @Override
  public void start(BundleContext context) {
    log.info("Spider: Starting trajectory planner");
    Trajectory trajectory = new TrajectoryPlanner();
    context.registerService(Trajectory.class, trajectory, null);
    log.info("Spider: Started trajectory planner");
  }

  @Override
  public void stop(BundleContext context) {
    log.info("Spider: Stopped trajectory planner.");
  }

  @Override
  public void serviceChanged(ServiceEvent event) {
  }

}