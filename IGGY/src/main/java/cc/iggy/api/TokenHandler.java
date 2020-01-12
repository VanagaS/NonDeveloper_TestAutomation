package cc.iggy.api;

/**
 * Created by VanagaS on 18-07-2017.
 * 
 */
public interface TokenHandler {
  String getLine(String variable);

  String getLine(String path, String variable);
}
