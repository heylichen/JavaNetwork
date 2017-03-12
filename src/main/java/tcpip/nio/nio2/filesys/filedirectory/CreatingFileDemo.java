package tcpip.nio.nio2.filesys.filedirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Chen Li on 2017/3/11.
 */
public class CreatingFileDemo {
  public static void main(String[] args) throws IOException, InterruptedException {
    //createFile
    Path newFile = Paths.get("test.txt");
    Files.createFile(newFile);
    //temp files
    Path tempDir = Paths.get(".");
    Path tempPath = Files.createTempFile(tempDir, "test", null);
    File tempFile = tempPath.toFile();

    System.out.println("sleep.");

    Thread.sleep(5000);
    tempFile.deleteOnExit();
  }
}
