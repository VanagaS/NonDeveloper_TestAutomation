package cc.iggy.core;

import cc.iggy.api.constants.ExitCode;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
class ConfigurationTest {

  @Test
  void onErrorThrowsRunTimeWithContinueOnErrorFalseExitCodeLessThanIG_NON_CRITICAL_BASE() {
    Configuration configuration = new Configuration();
    configuration.setContinueOnError(false);
    assertThrows(RuntimeException.class, new Executable() {
      public void execute() throws Throwable {
        configuration.onError(ExitCode.IG_FILE_NOT_FOUND);
      }
    });
  }

  @Test
  void onErrorThrowsRunTimeWithContinueTrueExitCodeGreaterThanIG_NON_CRITICAL_BASE() {
    Configuration configuration = new Configuration();
    configuration.setContinueOnError(true);
    assertThrows(RuntimeException.class, new Executable() {
      public void execute() throws Throwable {
        configuration.onError(ExitCode.EX_IOERR);
      }
    });
  }

  @Test
    void onErrorContinueTrueExitCodeLessThanIG_NON_CRITICAL_BASE() {
        Configuration configuration = new Configuration();
        configuration.setContinueOnError(true);
        IGGYAssertions.assertDoesntThrow(() -> {
            configuration.onError(ExitCode.IG_FILE_NOT_FOUND);
        });
    }

  @FunctionalInterface
  interface FailingExecution {
    void exec() throws RuntimeException;
  }

  static class IGGYAssertions {
    public static void assertDoesntThrow(FailingExecution execution) {
      try {
        execution.exec();
      } catch (RuntimeException ex) {
        throw new Error("RuntimeException not to be thrown! but thrown: ", ex);
      }
    }
  }
}
