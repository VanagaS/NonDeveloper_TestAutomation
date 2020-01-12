package cc.iggy.api;

import java.io.Reader;
import java.util.ArrayDeque;

/**
 * Created by VanagaS on 24-04-2017.
 * 
 */
public class BlockProcessor extends Processor {

  public BlockProcessor(Reader source) {
    super(source);
  }

  public BlockProcessor(ArrayDeque<Integer> level, Reader source) {
    super(level, source);
  }

  @Override
  Processor peekToNextBlock() {
    return null;
  }

  /**
   * Computes a result, or throws an exception if unable to do so.
   * 
   * @return computed result
   * 
   * @throws Exception if unable to compute a result
   */
  @Override
  public Processor call() throws Exception {
    return this;
  }
}
