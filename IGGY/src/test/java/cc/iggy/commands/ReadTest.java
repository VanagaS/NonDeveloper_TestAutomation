package cc.iggy.commands;

import cc.iggy.api.IGGYCommands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
class ReadTest {
  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void execute() {
    Read read = new Read();
    assertThrows(NullPointerException.class, new Executable() {
      public void execute() throws Throwable {
        IGGYCommands.Result<String> result = read.execute();
      }
    });
  }

}
