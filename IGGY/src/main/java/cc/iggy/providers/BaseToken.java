package cc.iggy.providers;

import cc.iggy.api.Position;
import cc.iggy.api.Processor;
import cc.iggy.api.Token;

import java.io.Closeable;
import java.io.Reader;
import java.util.ArrayDeque;

/**
 * Created by VanagaS on 29-04-2017.
 * 
 */
public abstract class BaseToken implements Token, AutoCloseable {


  /**
   * For those tokens that depend on an open and close tags, like token start, token end OR like
   * {@code $externalCommand()}, where external command depends on token start with "$" and token
   * end with ")", provided there is a non spaced, text which ends with "(", in between "$" and "(".
   * 
   * By default the tokenOpen value is {@code false} and will stay untouched for those tokens that
   * do not depend on this "open", "close" mechanism.
   */
  private boolean tokenOpen = false;


  /**
   * For those tokens that depend on an open and close tags, like token start, token end OR like
   * {@code $externalCommand()}, where external command depends on token start with "$" and token
   * end with ")", provided there is a non spaced, text which ends with "(", in between "$" and "(".
   * 
   * By default the tokenClosed value is {@code true} and will stay untouched for those tokens that
   * do not depend on this "open", "close" mechanism.
   */
  private boolean tokenClosed = true;

  /**
   * For those tokens that depend on an open and close tags, like token start, token end OR like
   * {@code $externalCommand()}, where external command depends on token start with "$" and token
   * end with ")", provided there is a non spaced, text which ends with "(", in between "$" and "(".
   * Tokens like {@code $externalCommand()} are considered as special tokens as they needed
   * additional check to realize whether the token is rightly identified.
   * 
   * By default the isSpecialToken value is {@code false}.
   */

  private boolean isSpecialToken = false;

  /**
   * For those tokens that depend on an open and close tags, like token start, token end OR like
   * {@code $externalCommand()}, where external command depends on token start with "$" and token
   * end with ")", provided there is a non spaced, text which ends with "(", in between "$" and "(".
   * Tokens like {@code $externalCommand()} are considered as special tokens as they needed
   * additional check to realize whether the token is rightly identified.
   * 
   * By default the isSpecialToken value is {@code false}.
   */
  private char nextRequiredChar = ' ';

  @Override
  public void init() {

  }

  @Override
  public void end() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void addChar(char c) {

  }

  @Override
  public boolean isNativeCommand() {
    return false;
  }

  @Override
  public boolean isRegisteredCommand() {
    return false;
  }

  @Override
  public Class getAssociatedClass() {
    return null;
  }

  @Override
  public Position getMark() {
    return null;
  }

  @Override
  public void setMark(Position position) {

  }

  @Override
  public String getString() {
    return null;
  }

  @Override
  public void setString(String str) {

  }

  @Override
  public char[] getChars() {
    return new char[0];
  }

  @Override
  public void getPreparedInstructions() {

  }

  @Override
  public void execute() {

  }

  @Override
  public void construct() {

  }

  @Override
  public boolean hasInstructions() {
    return false;
  }

  @Override
  public void setInstructions(String instructions) {

  }

  @Override
  public void identifyInstructions() {

  }

  @Override
  public boolean isVariableDefinition() {
    return false;
  }

  @Override
  public boolean isThreadDeclaration() {
    return false;
  }

  @Override
  public void setThreadParameters(int threadCount) {

  }

  @Override
  public boolean isExecuteLater() {
    return false;
  }

  @Override
  public boolean isMultiCommand() {
    return false;
  }

  @Override
  public void addTokenToMultiCommand(Token token) {

  }

  @Override
  public Token prepareChildToken() {
    return null;
  }

  /**
   * Closes this resource, relinquishing any underlying resources. This method is invoked
   * automatically on objects managed by the {@code try}-with-resources statement.
   * <p>
   * <p>
   * While this interface method is declared to throw {@code Exception}, implementers are
   * <em>strongly</em> encouraged to declare concrete implementations of the {@code close} method to
   * throw more specific exceptions, or to throw no exception at all if the close operation cannot
   * fail.
   * <p>
   * <p>
   * Cases where the close operation may fail require careful attention by implementers. It is
   * strongly advised to relinquish the underlying resources and to internally <em>mark</em> the
   * resource as closed, prior to throwing the exception. The {@code close} method is unlikely to be
   * invoked more than once and so this ensures that the resources are released in a timely manner.
   * Furthermore it reduces problems that could arise when the resource wraps, or is wrapped, by
   * another resource.
   * <p>
   * <p>
   * <em>Implementers of this interface are also strongly advised
   * to not have the {@code close} method throw {@link
   * InterruptedException}.</em>
   * <p>
   * This exception interacts with a thread's interrupted status, and runtime misbehavior is likely
   * to occur if an {@code InterruptedException} is {@linkplain Throwable#addSuppressed suppressed}.
   * <p>
   * More generally, if it would cause problems for an exception to be suppressed, the
   * {@code AutoCloseable.close} method should not throw it.
   * <p>
   * <p>
   * Note that unlike the {@link Closeable#close close} method of {@link Closeable}, this
   * {@code close} method is <em>not</em> required to be idempotent. In other words, calling this
   * {@code close} method more than once may have some visible side effect, unlike
   * {@code Closeable.close} which is required to have no effect if called more than once.
   * <p>
   * However, implementers of this interface are strongly encouraged to make their {@code close}
   * methods idempotent.
   *                                                        
   * @throws Exception if this resource cannot be closed
   */
  @Override
  public void close() throws Exception {

  }
}
