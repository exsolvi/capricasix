package se.exsolvi.capricasix.tray;

import java.awt.MenuItem;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayEventListener implements ActionListener {

  private TrayIcon trayIcon;

  public TrayEventListener(TrayIcon trayIcon) {
    this.trayIcon = trayIcon;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    MenuItem item = (MenuItem) e.getSource();
    if ("Error".equals(item.getLabel())) {
      trayIcon.displayMessage("Sun TrayIcon Demo", "This is an error message", TrayIcon.MessageType.ERROR);
    } else if ("Warning".equals(item.getLabel())) {
      trayIcon.displayMessage("Sun TrayIcon Demo", "This is a warning message", TrayIcon.MessageType.WARNING);
    } else if ("Info".equals(item.getLabel())) {
      trayIcon.displayMessage("Sun TrayIcon Demo", "This is an info message", TrayIcon.MessageType.INFO);
    } else if ("None".equals(item.getLabel())) {
      trayIcon.displayMessage("Sun TrayIcon Demo", "This is an ordinary message", TrayIcon.MessageType.NONE);
    }
  }
}
