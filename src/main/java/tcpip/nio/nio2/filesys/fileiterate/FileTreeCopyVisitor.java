package tcpip.nio.nio2.filesys.fileiterate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Chen Li on 2017/3/12.
 */
public class FileTreeCopyVisitor implements FileVisitor<Path> {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileTreeCopyVisitor.class);
  private Path from;
  private Path to;

  public FileTreeCopyVisitor(Path from, Path to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    LOGGER.info("-->{}", dir.toString());
    Path relative = from.relativize(dir);
    Files.createDirectories(to.resolve(relative));
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    Path relative = from.relativize(file);
    Files.createFile(to.resolve(relative));
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
}
