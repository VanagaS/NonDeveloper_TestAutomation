package cc.iggy.commands;

import cc.iggy.api.FileHandler;
import cc.iggy.api.IGGYCommands;
import cc.iggy.api.TokenHandler;
import cc.iggy.providers.DefaultTokenHandler;
import cc.iggy.providers.handlers.console.ConsoleHandler;
import cc.iggy.providers.handlers.file.FTPHandler;
import cc.iggy.providers.handlers.file.HTTPHandler;
import cc.iggy.providers.handlers.file.LocalFileHandler;
import cc.iggy.providers.handlers.file.SSHHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;


/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public class Read implements IGGYCommands {

  static Logger logger = LogManager.getLogger(Read.class.getName());

  /**
   * This method either has the actual execution commands (if no parameters are provided) or will
   * act as the orchestrator to dedicate the responsibility to other similar execute commands (must
   * be implemented) based on the amount and type of parameters.
   * 
   * @param params a list of parameters of String type.
   * @return Result an object holding the result of the execution.
   */
  @Override
  public <T> Result<T> execute(String... params) {
    switch (params.length) {
      case 0:
        /**
         * There is no parameter, it means to scan value from input.
         */
        System.out.println("Enter required value:");
        String input;

        new ConsoleHandler();
        return null;
      case 1:
        /**
         * There is 1 parameter, it could be a path to a local file or http or another handler.
         */
        return new Result<>((T) FileHandlerFactory.get((String) params[0],
            new DefaultTokenHandler()));
      case 2:
        return new Result<>((T) (FileHandlerFactory.get((String) params[0],
            new DefaultTokenHandler())).getLine((String) params[1]));
    }
    return null;
  }

  static class FileHandlerFactory {
    static FileHandler get(String path, TokenHandler handler) {
      if (path.startsWith("http")) {
        return new HTTPHandler(path);
      } else if (path.startsWith("ftp")) {
        return new FTPHandler(path);
      } else if (path.startsWith("ssh")) {
        return new SSHHandler(path);
      } else {
        return new LocalFileHandler(path, handler);
      }
    }
  }
}
