package cc.iggy.api.constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by VanagaS on 22-04-2017.
 * 
 */
class CommandsTest {

  @Test
  public void testEnumContains() {
    assertFalse(Commands.COMMANDS.contains("DONE"));
    assertTrue(Commands.COMMANDS.contains("SWITCH"));
  }

  @Test
  public void testEnumModifiability() {
    assertThrows(UnsupportedOperationException.class, new Executable() {
      public void execute() throws Throwable {
        Commands.COMMANDS.add("HELP");
      }
    });
  }
}
