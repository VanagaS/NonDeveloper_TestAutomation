package cc.iggy.api;

import java.io.Serializable;

/**
 * Created by VanagaS on 22-04-2017.
 * 
 */
public final class Position implements Serializable {

  /**
   * current line number from the source
   */
  int line = 0;

  /**
   * start position of first non-whitespace character on a line
   */
  int firstCharPositionAtLine = 0;

  /**
   * current location of character at current line
   */
  int column = 0;


  /**
   * Indentation of the current line
   */
  int currentIndent = 0;


  /**
   * Does the indentation include an IGGY command indentation? for ex: indent under "each": is not
   * required to be considered as a main indent
   */
  boolean commandIndent = false;

  public Position(int line, int firstCharPositionAtLine, int column, int currentIndent, boolean commandIndent) {
    this.line = line;
    this.firstCharPositionAtLine = firstCharPositionAtLine;
    this.column = column;
    this.currentIndent = currentIndent;
    this.commandIndent = commandIndent;
  }

  public Position(int line, int firstCharPositionAtLine, int column, int currentIndent) {
    this.line = line;
    this.firstCharPositionAtLine = firstCharPositionAtLine;
    this.column = column;
    this.currentIndent = currentIndent;
  }

  public Position() {}
}
