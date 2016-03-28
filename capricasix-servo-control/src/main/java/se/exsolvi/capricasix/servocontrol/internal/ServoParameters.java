package se.exsolvi.capricasix.servocontrol.internal;

import java.io.Serializable;

public class ServoParameters implements Serializable {

  private static final long serialVersionUID = 1L;

  private int fullMovementDelay;
  private int minimum;
  private int maximum;

  public ServoParameters(ServoParameters servoParameters) {
    this.maximum = servoParameters.maximum;
    this.minimum = servoParameters.minimum;
    this.fullMovementDelay = servoParameters.fullMovementDelay;
  }

  public ServoParameters() {
  }

  public int getMinimum() {
    return minimum;
  }

  public void setMinimum(int minimum) {
    this.minimum = minimum;
  }

  public int getMaximum() {
    return maximum;
  }

  public void setMaximum(int maximum) {
    this.maximum = maximum;
  }
 
  public void setFullMovementDelay(int delayInMilliseconds) {
    this.fullMovementDelay = delayInMilliseconds;
  }
  
  public int getFullMovementDelay() {
    return this.fullMovementDelay;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + fullMovementDelay;
    result = prime * result + maximum;
    result = prime * result + minimum;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ServoParameters other = (ServoParameters) obj;
    if (fullMovementDelay != other.fullMovementDelay)
      return false;
    if (maximum != other.maximum)
      return false;
    if (minimum != other.minimum)
      return false;
    return true;
  }
  
}