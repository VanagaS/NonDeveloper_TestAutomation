package cc.iggy.api.constants;


import cc.iggy.api.Character;

import static cc.iggy.api.constants.Context.AT_START;

/**
 * Created by VanagaS on 23-04-2017.
 * 
 */
public final class Context {

  public static final int AT_TOP_LEVEL = 0;
  public static final int AT_SECOND_LEVEL = 1;
  public static final int AT_THIRD_LEVEL = 2;
  public static final int AT_FOURTH_LEVEL = 4;
  public static final int AT_FIFTH_LEVEL = 8;
  public static final int AT_SIXTH_LEVEL = 16;

  /* Top level */
  public static final int AT_START = 0;
  public static final int AT_ROOT = 1;
  public static final int AT_NEW_LINE_START = 2;
  public static final int AT_KEY = 4;
  public static final int AT_VALUE = 8;
  public static final int AT_COMMAND = 16;
  public static final int AT_SPECIAL_CHARACTER = 32;

  /* Second level */
  /* Keys */
  public static final int AT_KEY_START = 1;
  public static final int AT_KEY_END = 2;
  public static final int AT_KEY_PARAMS = 4;
  public static final int AT_APPLY = 8;
  public static final int AT_MULTI_COMMAND = 16;
  /* Values */
  public static final int AT_SCALAR = 32;
  public static final int AT_BLOCK = 64;
  public static final int AT_FLOW = 128;
  /* Commands */
  public static final int AT_INCLUDES_BLOCK = 256; // defining aliases?
  public static final int AT_DATASET_BLOCK = 512;
  public static final int AT_INTERNAL_COMMAND = 1024;
  /* Special character */
  public static final int AT_CR = 2048;
  public static final int AT_ESCAPE = 4096;
  public static final int AT_ANCHOR = 8192;
  public static final int AT_ALIAS = 16_384;
  public static final int AT_TAG = 32_768;
  public static final int AT_DOCUMENT = 65_536; // for future(!)

  /* Third level */
  /* Keys */
  public static final int AT_INCL_VARIABLE = 1;
  public static final int AT_EXCL_VARIABLE = 2;
  public static final int AT_IF_CONDITION = 4;
  public static final int AT_ELSE_CONDITION = 8;
  public static final int AT_ELSIF_CONDITION = 16;
  public static final int AT_DO_CONDITION = 32;
  public static final int AT_WHILE_CONDITION = 64;
  public static final int AT_SWITCH_CONDITION = 128;
  public static final int AT_CASE_CONDITION = 256;
  public static final int AT_EACH_CONDITION = 512; // AT_FOR_CONDITION
  public static final int AT_BINDING_THREADS = 1024;
  public static final int AT_REPEATED_ACTIONS = 2048;
  public static final int AT_BATCH_COMMAND = 4096;
  public static final int AT_EXEC_COMMAND = 8192;
  /* Values */
  public static final int AT_SEQUENCE = 16_384;
  public static final int AT_MAPPING = 32_768;

  /* Fourth level */
  /* Keys */
  public static final int AT_MULTI_BINDING_THREADS = 1; // multiple threads for multiple binding
                                                        // files start[channel(2T10-20),a(3T5-10)]
  public static final int AT_EACH_AS_LIST = 2;
  public static final int AT_EACH_AS_FOR = 4;
  public static final int AT_EACH_AS_DATASET = 8;
  public static final int AT_SHARED_REPEATED_ACTION = 16;
  public static final int AT_BATCH_WITH_NAME = 32;
  public static final int AT_BATCH_WITH_COMMAND = 64;
  public static final int AT_EXEC_WITH_PARAMS = 128;
  /* Values */
  public static final int AT_SEQUENCE_START = 256;
  public static final int AT_SEQUENCE_END = 512;
  public static final int AT_MAPPING_START = 1024;
  public static final int AT_MAPPING_END = 2048;

  /* Fifth level */
  /* Keys */
  public static final int AT_EACH_AS_LIST_WITH_THREADS = 0;
  public static final int AT_EACH_AS_FOR_WITH_THREADS = 1;
  public static final int AT_EACH_AS_DATASET_WITH_THREADS = 2;
  public static final int AT_MULTI_FOR_VARIABLES = 4;

  /* Context identifiers: single */
  public static final Character NEWLINE = new Char('\n');
  public static final Character RETURN = new Char('\r');
  public static final Character EOF = new Char('\0');
  public static final Character WS = new Char(' ');
  public static final Character TAB = new Char('\t');
  public static final Character COMMAND_START = new Char('$');
  public static final Character COMMAND = new Char('(');
  public static final Character COMMAND_END = new Char(')');
  public static final Character KEY_EXT_START = new Char('[');
  public static final Character DOUBLE_QOUTE = new Char('"');
  public static final Character SINGLE_QUOTE = new Char('\'');

  /* Special IGGYSpecials */

  /**
   *    32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47
   *  space  !   "   #   $   %   &   '   (   )   *   +   ,   -   .   /
   *
   *    48  - 57
   *     0  -  9
   *
   *    58  59  60  61  62  63  64
   *     :   ;   <   =   >   ?   @
   *
   *    65  - 90
   *     A  -  Z
   *
   *    91  92  93  94  95  96
   *     [   \   ]   ^   _   `
   *
   *    97  - 122
   *     a  -   z
   *
   *    123  124  125  126
   *      {    |    }    ~
   */

  /* place holders for IGGY special values */
  public static final int ASCII_RANGE = 128;
  static final boolean[] IS_IGGY_KEY = new boolean[ASCII_RANGE];
  static final boolean[] IS_IGGY_VALUE = new boolean[ASCII_RANGE];

  /* Context identifiers: multi */
  public static final Character IGGY_KEY = new IGGYSpecials(".[]()+^,:;~@=", IS_IGGY_KEY); // 46 91 93 40 41 43 94 44 58 59 126 64 61
  public static final Character IGGY_VALUE = new IGGYSpecials("{}[]$()<>", IS_IGGY_VALUE); // 123 125 91 93 36 40 41 60 62
  // public static final Character LINE_BREAK = new IGGYSpecials("\r\n"); // CR 13, LF 10

  private Context() {
  }

  // public static Context INSTANCE = new Context();
  public static final ThreadLocal<LocalContext> LOCAL_CONTEXT = ThreadLocal.withInitial(LocalContext::new);
  public static final LocalContext CONTEXT = LOCAL_CONTEXT.get();
}

class LocalContext {
    private int previousContext = AT_START;
    int currentContext = AT_START;
    int location; // level_one (at new line, at key, at value)
    int level_two; // can be one or more values at level two
    int level_three;
    int level_four;
    int level_five;
    int AT_LEVEL;

    public void setCurrentContext(int currentContext) {
        this.previousContext = this.currentContext;
        this.currentContext = currentContext;
    }

    public void setLocation(int location) {
        this.location |= location;
    }

    public void setLevel_two(int level_two) {
        this.level_two |= level_two;
    }

    public void setLevel_three(int level_three) {
        this.level_three |= level_three;
    }

    public void setLevel_four(int level_four) {
        this.level_four |= level_four;
    }

    public void setLevel_five(int level_five) {
        this.level_five |= level_five;
    }
}
