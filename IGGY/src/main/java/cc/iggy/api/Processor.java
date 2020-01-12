package cc.iggy.api;

import cc.iggy.api.constants.Context;
import cc.iggy.exceptions.EOFReachedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.concurrent.Callable;

/**
 * Created by VanagaS on 23-04-2017.
 * 
 */
public abstract class Processor implements Callable, Serializable {

  private static Logger logger = LogManager.getLogger(Processor.class.getName());

  /**
   * The location details of the current character being parsed.
   */
  private final Position position = new Position();

  /**
   * IGGY hierarchy for native commands and actions Default size for ArrayDeque is 16, which should
   * be sufficient for us
   * <p>
   * If a thread is created, a new object of this class is instantiated and this stack is assigned
   * to its AT_LEVEL value using constructor {@code new Processor(AT_LEVEL)}.
   */
  private final ArrayDeque<Integer> AT_LEVEL;

  private final Reader source;
  /**
   * In which context, the current processor instance is in?
   */
  private int context = Context.AT_START;
  private char previousChar;
  private char currentChar;
  private char nextChar;

  /**
   * The current token being processed.
   */
  private Token currentToken = null;

  public Processor(Reader source) {
    AT_LEVEL = new ArrayDeque<>();
    this.source = source;
  }

  public Processor(ArrayDeque<Integer> level, Reader source) {
    AT_LEVEL = level;
    this.source = source;
  }

  public Processor add(char ch) {
    //CONTENT.append(ch);
    //CONTENT_UCASE.append(Character.toUpperCase(ch));
    return this;
  }

  private Processor addToMask(int mask) {
    int at_level = AT_LEVEL.pop();
    AT_LEVEL.push(at_level | mask);
    return this;
  }

  private Processor removeFromMask(int mask) {
    int at_level = AT_LEVEL.pop();
    AT_LEVEL.push(at_level ^ mask);
    return this;
  }

  private Processor next() {
    try {
      int character = source.read();
      if (character == -1) {
        // EOF reached
        return this;
      }
      if (character == '\n') {
        position.line++;
        position.column = 0;
      }
    } catch (IOException ex) {
      logger.error("ExitCode reading from source: [{} @ {}]", ex.toString(), ex.getStackTrace()[0]);
    } catch (NullPointerException ex) {
      logger.error("Source cannot be null: [{} @ {}]", ex.toString(), ex.getStackTrace()[0]);
    }
    return this;
  }

  private Processor skipWhiteSpace() {
    try {
      int character;
      do {
        character = source.read();
      } while (Character.isWhitespace(character));
    } catch (IOException ex) {
      logger.error("ExitCode reading from source: [{} @ {}]", ex.toString(), ex.getStackTrace()[0]);
    } catch (NullPointerException ex) {
      logger.error("Source cannot be null: [{} @ {}]", ex.toString(), ex.getStackTrace()[0]);
    }
    return this;
  }

  private Processor checkEOF(int c) throws EOFReachedException {
    if (c == -1) {
      logger.error("EOF reached!!");
      throw new EOFReachedException("EOF Reached!");
    }
    return this;
  }

  abstract Processor peekToNextBlock();
}
