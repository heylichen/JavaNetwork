package tcpip.nio.nio2.filesys.writing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class WriteAllDemo {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("usage: WriteAllDemo URL");
      return;
    }
    URL url = new URL(args[0]);
    InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    List<String> lines = bufferedReader.lines().collect(Collectors.toList());

    Files.write(Paths.get("page.html"), lines);
  }
}
