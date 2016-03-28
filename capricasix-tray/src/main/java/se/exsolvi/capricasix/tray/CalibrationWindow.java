package se.exsolvi.capricasix.tray;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class CalibrationWindow {

  private CapricasixController controller;
  private final JFrame frame;

  public CalibrationWindow(CapricasixController controller) {
    this.controller = controller;

    frame = new JFrame("Calibration");
    JLabel label = new JLabel("Ready for calibration", JLabel.CENTER);

    frame.add(label);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setSize(650, 400);
    frame.setLocation(300, 300);
    
    
  }

  public void show() {
    frame.setVisible(true);
  }

}
