package se.exsolvi.capricasix.tray;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TrayGUI {

  private final TrayIcon trayIcon;
  private SystemTray tray;
  protected CapricasixController controller;

  public TrayGUI() {
    controller = new CapricasixController();
    final PopupMenu popup = new PopupMenu();
    trayIcon = new TrayIcon(createImage("images/tray.jpg", "tray icon"));

    // Create a popup menu components
    MenuItem aboutItem = new MenuItem("About");
    MenuItem calibrateItem = new MenuItem("Calibrate");
    CheckboxMenuItem stopItem = new CheckboxMenuItem("Stop!");
    CheckboxMenuItem reinitItem = new CheckboxMenuItem("Reinit");
    Menu displayMenu = new Menu("Display");
    MenuItem errorItem = new MenuItem("Error");
    MenuItem warningItem = new MenuItem("Warning");
    MenuItem infoItem = new MenuItem("Info");
    MenuItem noneItem = new MenuItem("None");
    MenuItem exitItem = new MenuItem("Exit");

    // Add components to popup menu
    popup.add(aboutItem);
    popup.addSeparator();
    popup.add(calibrateItem);
    popup.add(reinitItem);
    popup.add(stopItem);
    popup.addSeparator();
    popup.add(displayMenu);
    displayMenu.add(errorItem);
    displayMenu.add(warningItem);
    displayMenu.add(infoItem);
    displayMenu.add(noneItem);
    popup.add(exitItem);

    trayIcon.setPopupMenu(popup);

    trayIcon.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Capricasix version 1.0");
      }
    });

    aboutItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "This dialog box is run from the About menu item");
      }
    });

    calibrateItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new CalibrationWindow(controller).show();
      }
    });

    stopItem.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        controller.stop();
      }
    });

    ActionListener listener = new TrayEventListener(trayIcon);

    errorItem.addActionListener(listener);
    warningItem.addActionListener(listener);
    infoItem.addActionListener(listener);
    noneItem.addActionListener(listener);

    reinitItem.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        controller.reinit();
      }
    });

    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tray.remove(trayIcon);
        System.exit(0);
      }
    });

  }

  public void show() {
    // Check the SystemTray support
    if (!SystemTray.isSupported()) {
      System.out.println("SystemTray is not supported");
      return;
    }
    try {
      trayIcon.setImageAutoSize(true);
      tray = SystemTray.getSystemTray();
      tray.add(trayIcon);
    } catch (AWTException e) {
      System.out.println("TrayIcon could not be added.");
      return;
    }

  }

  // Obtain the image URL
  protected static Image createImage(String path, String description) {
    URL imageURL = Monitor.class.getResource(path);

    if (imageURL == null) {
      System.err.println("Resource not found: " + path);
      return null;
    } else {
      return (new ImageIcon(imageURL, description)).getImage();
    }
  }

}
