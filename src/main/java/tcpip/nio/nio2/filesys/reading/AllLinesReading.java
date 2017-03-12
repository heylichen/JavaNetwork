package tcpip.nio.nio2.filesys.reading;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class AllLinesReading {
  private static Logger LOGGER = LoggerFactory.getLogger(AllLinesReading.class);

  public static void main(String[] args) throws IOException {
    String path = "C:\\work\\github\\JavaNetwork\\src\\main\\resources\\tcpip\\nio\\nio2\\filesys\\reading\\reading.txt";
    List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    LOGGER.info("lines:{}", JSON.toJSONString(lines));

  }
}
