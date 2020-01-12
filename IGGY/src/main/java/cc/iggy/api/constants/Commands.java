package cc.iggy.api.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * Created by VanagaS on 22-04-2017.
 * 
 */
public class Commands {

  private static String[] _COMMANDS = new String[] {"INCLUDE", "CONSOLE", "LOG", "ERR", "WARN",
      "EXIT", "SLEEP", "EACH", "IF", "ELSE", "ELSIF", "DEBUG-EXIT", "WAIT", "BREAK", "CONTINUE",
      "BATCH", "EXEC", "BATCH-EXEC", "APPLY", "SWITCH", "DEFAULT", "WHILE", "DO", "TRY", "THROW",};


  /**
   * This field is only accessed to check if a particular 'command' exists (or is defined) in
   * _COMMANDS. Unmodifiable Set is preferred, so as to prevent adding an element external to this
   * Object
   * <p>
   * Use of Set is preferred here instead of directly using ArrayList which can be easily obtained
   * by Arrays.asList (which is an internal private "read-only" ArrayList and exactly what we need),
   * however:
   * <p>
   * ArrayList.contains() => O(n) HashSet.contains() => O(1)
   * <p>
   * hence Set is preferred here.
   */
  public static final Set<String> COMMANDS = unmodifiableSet(new HashSet<String>(
      Arrays.asList(_COMMANDS)));
}
