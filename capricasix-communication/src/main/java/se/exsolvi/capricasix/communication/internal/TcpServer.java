package se.exsolvi.capricasix.communication.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.trajectory.Trajectory;

public class TcpServer implements Runnable {

  private static final int PORT = 7777;

  private boolean running = true;
  private ServerSocket socket;
  private BufferedReader bufferedReader;
  private static final Logger log = LoggerFactory.getLogger(TcpServer.class);
  private ServiceTracker<Trajectory, Trajectory> trajectoryPlannerTracker;

  public TcpServer() {
    BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
    trajectoryPlannerTracker = new ServiceTracker<Trajectory, Trajectory>(context, Trajectory.class, null);
    trajectoryPlannerTracker.open();
  }

  @Override
  public void run() {
    log.info("TCP Server starting");
    try {
      socket = new ServerSocket(PORT);
      // socket.setSoTimeout(1000);
      bufferedReader = new BufferedReader(new InputStreamReader(socket.accept().getInputStream()));
      log.info("TCP Server initialized");
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("TCP Server started");
    try {
      while (running) {
        String command = bufferedReader.readLine().trim().toUpperCase();
        handleCommand(command);
      }
    } catch (IOException e) {
      e.printStackTrace();
      try {
        socket.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    log.info("TCP Server stopped");
  }

  private void handleCommand(String commandLine) {
    String command = commandLine.split(" ")[0];
    switch (command) {
      case "QUIT":
        log.info("Command: QUIT");
        stop();
        break;
      case "MOVE":
        log.info("Command: MOVE");
        int position = Integer.parseInt(commandLine.split(" ")[1]);
        try {
          Trajectory trajectory = trajectoryPlannerTracker.waitForService(1000);
          if (trajectory != null) {
            trajectory.move(position);
          } else {
            log.error("Trajectory service not available");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        break;
    }
  }

  public void stop() {
    running = false;
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
