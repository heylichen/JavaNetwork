package tcpip.nio.nio2.filesys.fileiterate;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class FileVisitorDemo {
  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("usage FileVisitorDemo path");
      return;
    }

    final Path startDir = Paths.get(args[0]);
    Files.walkFileTree(startDir, new FileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("--->"+dir.toString());
        if (dir.equals(startDir)) {
          return FileVisitResult.CONTINUE;
        } else{
          return FileVisitResult.SKIP_SUBTREE;
        }
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.getFileName().toString());
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }
    });
  }
}
