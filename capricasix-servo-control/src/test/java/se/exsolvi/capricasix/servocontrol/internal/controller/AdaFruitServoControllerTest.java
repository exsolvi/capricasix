package se.exsolvi.capricasix.servocontrol.internal.controller;

import static org.junit.Assert.fail;
import static org.powermock.api.support.membermodification.MemberMatcher.*;
import static org.powermock.api.support.membermodification.MemberModifier.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.PowerMockitoCore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Matchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AdaFruitServoController.class})
public class AdaFruitServoControllerTest {
  
  private AdaFruitServoController controller; 
  
  @Mock(answer=Answers.RETURNS_SMART_NULLS)
  private AdafruitPCA9685 hw;
  
  @Before
  public void before() throws Exception {
    PowerMockito.whenNew(AdafruitPCA9685.class).withAnyArguments().thenReturn(hw);
    PowerMockito.doNothing().when(hw, method(AdafruitPCA9685.class, "setPWMFreq")).withArguments(anyInt());
    controller = new AdaFruitServoController();
    int numberOfServos = (int) Math.round(Math.random()*10);
    controller.init(numberOfServos);
  }

  @Test
  public void testReleaseAll() throws Exception {
    PowerMockito.doAnswer(new Answer<Void>() {
     @Override
    public Void answer(InvocationOnMock invocation) throws Throwable {
      System.out.println(invocation.toString());
      return null;
    }
    }).when(hw, method(AdafruitPCA9685.class, "setPWM")).withArguments(anyInt(), anyInt(), anyInt());
    controller.releaseAll();
  }
  
}
