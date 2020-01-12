package cc.iggy.api;

import cc.iggy.utils.Reflections;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by VanagaS on 23-04-2017.
 * 
 */
class ProcessorTest {
  @Test
  void add() {}

  @Test
  void peekToNextBlock() {}

  @Test
  void next() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Processor processor = new Processor(null) {
      @Override
      Processor peekToNextBlock() {
        return null;
      }

      @Override
      public Object call() throws Exception {
        return null;
      }
    };
    Method next = Reflections.setMethodAccessible(Processor.class, "next", null);
    Object obj = next.invoke(processor, null);
  }

}
