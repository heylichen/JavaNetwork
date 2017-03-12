package tcpip.nio.nio2.filesys.reading;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class StreamReading {
  public static void main(String[] args) throws IOException {
    String path = "C:\\work\\github\\JavaNetwork\\src\\main\\resources\\tcpip\\nio\\nio2\\filesys\\reading\\reading.txt";
    BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
    reader.lines().forEach(a -> System.out.println(a));
  }
}
