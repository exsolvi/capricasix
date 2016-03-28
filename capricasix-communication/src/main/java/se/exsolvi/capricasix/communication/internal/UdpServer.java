package se.exsolvi.capricasix.communication.internal;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.joystickcontroller.JoystickController;
import se.exsolvi.joystickgw.JoystickState;

public class UdpServer implements Runnable {

  private static final int PORT = 6666;
  private final static int PACKETSIZE = 150;

  private static final Logger log = LoggerFactory.getLogger(UdpServer.class);
  private DatagramSocket socket;

  private boolean running = true;
  private ServiceTracker<JoystickController, JoystickController> joystickTracker;

  public UdpServer() {
    BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
    joystickTracker = new ServiceTracker<JoystickController, JoystickController>(context, JoystickController.class, null);
    joystickTracker.open();
    try {
      socket = new DatagramSocket(PORT);
      socket.setSoTimeout(1000);
      log.info("UDP Server initialized");
    } catch (SocketException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void run() {
    log.info("UDP Server started");
    boolean connected = false;
    while (running) {
      DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
      try {
        socket.receive(packet);
        if (!connected) {
          log.info("Joystick connected");
          connected = true;
        }
        byte[] data = packet.getData();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        TProtocol protocol = new TBinaryProtocol(new TIOStreamTransport(new BufferedInputStream(bais)));
        JoystickState state = new JoystickState();
        state.read(protocol);
        JoystickController joystickController;
        try {
          joystickController = joystickTracker.waitForService(1000);
        } catch (InterruptedException e) {
          continue;
        }
        if (joystickController != null) {
          joystickController.handleJoystickState(state);
        } else {
          log.warn("Joystick controller not available");
        }
      } catch (SocketTimeoutException stoe) {
        if (connected) {
          log.info("Joystick disconnected");
          connected = false;
        }
      } catch (IOException | TException e) {
        log.error("Exception in UDP Server", e);
      }
    }
    log.info("UDP Server stopped");
  }

  public void stop() {
    running = false;
    socket.disconnect();
    socket.close();
  }

}
