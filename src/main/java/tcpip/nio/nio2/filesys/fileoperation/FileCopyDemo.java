package tcpip.nio.nio2.filesys.fileoperation;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class FileCopyDemo {
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("usage FileCopyDemo fromUrl toPath");
      return;
    }
    URL url = new URL(args[0]);
    Files.copy(url.openStream(), Paths.get("target", args[1] + ".html"), StandardCopyOption.REPLACE_EXISTING);
  }
}
