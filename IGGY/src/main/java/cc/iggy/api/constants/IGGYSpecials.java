package cc.iggy.api.constants;

import cc.iggy.api.Character;

/**
 * Created by VanagaS on 27-04-2017.
 * 
 */
public class IGGYSpecials implements Character {

  // TODO: private final static Character[] IGGY_SPECIALS = new Character[128];

  public IGGYSpecials(String string, boolean[] bytes) {
    for (int i = 0; i < string.length(); i++) {
      int c = string.codePointAt(i);
      bytes[c] = true;
    }
  }

  public boolean contains(char c, boolean[] specials) {
    return specials[c];
  }

  public boolean containsByContext(char c) {
    if((Context.CONTEXT.location & Context.AT_KEY) == Context.AT_KEY) {
      return Context.IS_IGGY_KEY[c];
    } else if((Context.CONTEXT.currentContext & Context.AT_VALUE) == Context.AT_VALUE) {
      return Context.IS_IGGY_VALUE[c];
    }
    return false;
  }

  @Override
  public boolean is(char c) {
    return containsByContext(c);
  }
}
