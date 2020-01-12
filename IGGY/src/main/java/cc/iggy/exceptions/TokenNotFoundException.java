package cc.iggy.exceptions;

/**
 * Created by VanagaS on 27-04-2017.
 * 
 */
public class TokenNotFoundException extends Exception {

  /**
   * Creates a new {@link TokenNotFoundException}
   * 
   * @param message the exception message.
   */
  public TokenNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates a new {@link TokenNotFoundException} with the given exception stacktrace. This
   * constructor copies the stacktrace and the message from the passed in {@code Throwable} into
   * this exception.
   * 
   * @param ex the cause for exception
   */
  public TokenNotFoundException(Throwable ex) {
    super(ex);
  }
}
