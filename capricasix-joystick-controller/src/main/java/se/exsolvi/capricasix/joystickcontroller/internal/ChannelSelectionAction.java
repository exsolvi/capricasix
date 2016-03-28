package se.exsolvi.capricasix.joystickcontroller.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.servocontrol.ServoController;
import se.exsolvi.joystickgw.JoystickState;

public class ChannelSelectionAction extends AbstractJoystickAction {

  protected Logger log = LoggerFactory.getLogger(ChannelSelectionAction.class);

  private boolean channelSelectionActive = false;

  @Override
  public void act(JoystickState state) {
    if (!channelSelectionActive && state.isFire() && state.isButtonF1()) {
      log.info("Starting channel selection");
      channelSelectionActive = true;
    }
    if (channelSelectionActive && state.isFire() && state.isButton2()) {
      log.info("Stopping channel selection");
      channelSelectionActive = false;
    }
    if (channelSelectionActive) {
      ServoController servoController = null;
      try {
        servoController = servoControllerServiceTracker.waitForService(5000);
      } catch (InterruptedException e) {
        // Ignore
      }
      if (channelSelectionActive) {
        switch (state.getTumbstick()) {
        case NORTH:
          servoController.setChannel(0);
          log.info("Selected channel {}", 0);
          channelSelectionActive = false;
          break;
        case NORTHEAST:
          servoController.setChannel(1);
          log.info("Selected channel {}", 1);
          channelSelectionActive = false;
          break;
        case EAST:
          servoController.setChannel(2);
          log.info("Selected channel {}", 2);
          channelSelectionActive = false;
          break;
        case SOUTHEAST:
          servoController.setChannel(3);
          log.info("Selected channel {}", 3);
          channelSelectionActive = false;
          break;
        case SOUTH:
          servoController.setChannel(4);
          log.info("Selected channel {}", 4);
          channelSelectionActive = false;
          break;
        case SOUTHWEST:
          servoController.setChannel(5);
          log.info("Selected channel {}", 5);
          channelSelectionActive = false;
          break;
        case WEST:
          servoController.setChannel(6);
          log.info("Selected channel {}", 6);
          channelSelectionActive = false;
          break;
        case NORTHWEST:
          servoController.setChannel(7);
          log.info("Selected channel {}", 7);
          channelSelectionActive = false;
          break;
        default:
          break;
        }
      }
    }
  }
  
}
