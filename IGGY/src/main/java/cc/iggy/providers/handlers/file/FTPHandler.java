package cc.iggy.providers.handlers.file;

import cc.iggy.api.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public class FTPHandler implements FileHandler {

  private final String path;
  private final File file;

  public FTPHandler(String path) {
    this.path = path;
    this.file = fetchFile(path);
  }

  @Override
  public File fetchFile(String path) {
    return null;
  }

  @Override
  public String getLine(String variable) {
    return null;
  }

  @Override
  public String getLine(String path, String variable) {
    return null;
  }

  /**
   * Closes this stream and releases any system resources associated with it. If the stream is
   * already closed then invoking this method has no effect.
   * <p>
   * <p>
   * As noted in {@link AutoCloseable#close()}, cases where the close may fail require careful
   * attention. It is strongly advised to relinquish the underlying resources and to internally
   * <em>mark</em> the {@code Closeable} as closed, prior to throwing the {@code IOException}.
   * 
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {

  }
}
