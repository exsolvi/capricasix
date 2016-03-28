package se.exsolvi.capricasix.servocontrol.internal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ServoParametersTest {

  private ServoParameters parameters;
  private int randomMax;
  private int randomMin;
  private int randomFullMovementDelay;

  @Before
  public void createInstance() {
    randomMax = (int) (Math.random() * 1000);
    randomMin = (int) (Math.random() * 1000);
    randomFullMovementDelay = (int) (Math.random() * 1000);
    parameters = new ServoParameters();
    parameters.setMaximum(randomMax);
    parameters.setMinimum(randomMin);
    parameters.setFullMovementDelay(randomFullMovementDelay);
  }

  @Test
  public void testConstructorWithParameter() {
    ServoParameters constructed = new ServoParameters(parameters);
    assertEquals(parameters, constructed);
  }
  
  @Test
  public void testGetMinimum() {
    assertEquals(randomMin, parameters.getMinimum());
  }

  @Test
  public void testGetMaximum() {
    assertEquals(randomMax, parameters.getMaximum());
  }

  @Test
  public void testGetFullMovementDelay() {
    assertEquals(randomFullMovementDelay, parameters.getFullMovementDelay());
  }

}
