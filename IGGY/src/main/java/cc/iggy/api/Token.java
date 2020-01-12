package cc.iggy.api;


import cc.iggy.api.constants.Char;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;

/**
 * Created by VanagaS on 22-04-2017.
 *
 */
public interface Token extends AutoCloseable, Callable {

    ThreadLocal<ArrayDeque<Token>> CHILD_TOKENS = ThreadLocal.withInitial(ArrayDeque::new);
    ThreadLocal<ArrayDeque<Character>> BUFFER = ThreadLocal.withInitial(ArrayDeque::new);

    void init();

    void end();

    void destroy();

    default void addChar(char c) {
        BUFFER.add(new Char(c));
    }

    boolean isNativeCommand();

    boolean isRegisteredCommand();

    Class getAssociatedClass();

    Position getMark();

    void setMark(Position position);

    String getString();

    void setString(String str);

    char[] getChars();

    void getPreparedInstructions(); // commands stacked for delayed execution

    void execute(); // Command Pattern

    void construct();

    boolean hasI    nstructions();

    void setInstructions(String instructions);

    void identifyInstructions();

    boolean isVariableDefinition();

    boolean isThreadDeclaration();

    void setThreadParameters(int threadCount);

    boolean isExecuteLater(); // not for thread safe implementation

    boolean isMultiCommand(); // hello+world+click

    void addTokenToMultiCommand(Token token); // queue FIFO

    Token prepareChildToken(); // for multi command
}
