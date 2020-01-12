package cc.iggy.api;

import java.io.Closeable;
import java.io.File;
import java.io.Serializable;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public interface FileHandler extends TokenHandler, Serializable, Closeable {
  File fetchFile(String path);
}
