package cc.iggy.core;

import cc.iggy.api.constants.ExitCode;

import java.io.Serializable;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public class Configuration implements Serializable {
  private boolean continueOnError = false;

  public void onError(int errorCode) {
    if (!continueOnError || (errorCode > ExitCode.IG_NON_CRITICAL_BASE)) {
      throw new RuntimeException("IGGY exit with exit code: " + errorCode);
    }
  }

  public boolean isContinueOnError() {
    return continueOnError;
  }

  public void setContinueOnError(boolean continueOnError) {
    this.continueOnError = continueOnError;
  }
}
