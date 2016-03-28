package se.exsolvi.capricasix.joystickcontroller.internal;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.exsolvi.capricasix.joystickcontroller.JoystickController;
import se.exsolvi.joystickgw.JoystickState;

public class JoystickControllerImpl implements JoystickController {

  private final static Logger log = LoggerFactory.getLogger(JoystickControllerImpl.class);
  private List<AbstractJoystickAction> actions = new ArrayList<AbstractJoystickAction>();

  public JoystickControllerImpl() {
    actions.add(new CalibrationAction());
    actions.add(new ReleaseAllAction());
    actions.add(new JogAction());
    actions.add(new ChannelSelectionAction());
    actions.add(new JogAllLowerAction());
    actions.add(new JogAllUpperAction());
    actions.add(new SpeedMeasurementAction());
  }

  @Override
  public void handleJoystickState(JoystickState state) {
    // log.debug("Recieved joystick event, dispatching to all handler classes");
    for (AbstractJoystickAction action : actions) {
      // log.debug("Found handler class: {}", action.getClass());
      action.act(state);
    }

  }

}
