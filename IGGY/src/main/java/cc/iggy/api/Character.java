package cc.iggy.api;

/**
 * Created by VanagaS on 27-04-2017.
 *
 */
public interface Character {
    boolean is(char c);
    default boolean isNot(char c) {
        return !is(c);
    }
}