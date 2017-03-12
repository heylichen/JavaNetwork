package tcpip.nio.nio2.filesys.fileiterate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class CopyFileTreeDemo {
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("usage CopyFileTreeDemo fromPath toPath. please do not input large directory.");
      return;
    }
    Path startPath = Paths.get(args[0]);
    FileTreeCopyVisitor copyVisitor = new FileTreeCopyVisitor(startPath, Paths.get(args[1]));
    Files.walkFileTree(startPath, copyVisitor);
  }
}
