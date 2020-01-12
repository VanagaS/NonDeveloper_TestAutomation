package cc.iggy.exceptions;

/**
 * Created by VanagaS on 27-04-2017.
 * 
 */
public class EOFReachedException extends Exception {

  /**
   * Creates a new {@link EOFReachedException}
   *
   * @param message the exception message.
   */
  public EOFReachedException(String message) {
    super(message);
  }

  /**
   * Creates a new {@link EOFReachedException} with the given exception stacktrace. This
   * constructor copies the stacktrace and the message from the passed in {@code Throwable} into
   * this exception.
   *
   * @param ex the cause for exception
   */
  public EOFReachedException(Throwable ex) {
    super(ex);
  }
}
