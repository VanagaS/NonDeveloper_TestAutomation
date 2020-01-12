package cc.iggy.api.constants;

import cc.iggy.api.Character;

/**
 * Created by VanagaS on 24-07-2017.
 *
 */
public class Char implements Character {
        int _char;
        public Char(char c) {
            this._char = c;
        }

        @Override
        public boolean is(char c) {
            return this._char == c;
        }
}
