package se.exsolvi.capricasix.communication;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.communication.internal.TcpServer;
import se.exsolvi.capricasix.communication.internal.UdpServer;

public class Activator implements BundleActivator, ServiceListener {

  private final static Logger log = LoggerFactory.getLogger(Activator.class);
  private UdpServer udpServer;
  private TcpServer tcpServer;

  @Override
  public void start(BundleContext context) {
    udpServer = new UdpServer();
    Thread udpServer = new Thread(this.udpServer);
    udpServer.start();
    log.info("Started Capricasix communication bunldle - UDP");
    tcpServer = new TcpServer();
    Thread tcpServer = new Thread(this.tcpServer);
    tcpServer.start();
    log.info("Started Capricasix communication bunldle - TCP");
  }

  @Override
  public void stop(BundleContext context) {
    udpServer.stop();
    tcpServer.stop();
    log.info("Stopped Capricasix communication bunldle");
  }

  @Override
  public void serviceChanged(ServiceEvent event) {
  }

}