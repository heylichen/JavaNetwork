package tcpip.nio.nio2.filesys.directory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class DirectoryStreamDemo {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("usage DirectoryStreamDemo directoryPath [filterType]");
      return;
    }

    DirectoryStream.Filter<Path> directoryFilter = null;
    DirectoryStream<Path> directoryStream;
    if (args.length == 2) {
      String postFix = args[1].trim();
      directoryFilter = a -> a.toString().endsWith(postFix);
      directoryStream = Files.newDirectoryStream(Paths.get(args[0]), directoryFilter);
    } else {
      directoryStream = Files.newDirectoryStream(Paths.get(args[0]));
    }

    try {
      for (Path path : directoryStream) {
        System.out.println(path.getFileName());
      }
    } finally {
      directoryStream.close();
    }
  }
}
