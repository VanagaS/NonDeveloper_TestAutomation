package cc.iggy.api;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public interface IGGYCommands {

  /**
   * This method either has the actual execution commands (if no parameters are provided) or will
   * act as the orchestrator to dedicate the responsibility to other similar execute commands (must
   * be implemented) based on the amount and type of parameters.
   * 
   * @param params a list of parameters of String type.
   * @return Result an object holding the result of the execution.
   */
  <T> Result execute(String... params);


  /**
   * A class for simplicity (instead of a HashMap) to hold the result Object as part of execution.
   */
  class Result<T> {
    T object;

    public Result(T object) {
      this.object = object;
    }

    T get() {
      return this.object;
    }
  }
}
