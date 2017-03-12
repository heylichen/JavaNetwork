package tcpip.nio.nio2.filesys.filesystemprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
  private static final Logger LOGGER = LoggerFactory.getLogger(PathDemo.class);

  public static void main(String[] args) throws IOException {

//    relativeAndAbsolutePaths();
//    normalizeResolve();
    additionalCapabilities();

  }

  private static void basicPathMethods() {
    FileSystem fsDefault = FileSystems.getDefault();
    Path path = fsDefault.getPath("a", "b", "c");
    System.out.println(path);
    System.out.printf("File name: %s%n", path.getFileName());
    for (int i = 0; i < path.getNameCount(); i++)
      System.out.println(path.getName(i));
    System.out.printf("Parent: %s%n", path.getParent());
    System.out.printf("Root: %s%n", path.getRoot());
    System.out.printf("SubPath [0, 2): %s%n", path.subpath(0, 2));
    System.out.println(path.toAbsolutePath().getRoot());
  }

  private static void relativeAndAbsolutePaths() {
    FileSystem fsDefault = FileSystems.getDefault();
    Path path = fsDefault.getPath("a", "b", "c");
    LOGGER.info("path:{} ,isAbsolute:{}", path, path.isAbsolute());

    for (Path rootPath : fsDefault.getRootDirectories()) {
      LOGGER.info("path:{} ,isAbsolute:{}, root:{}", rootPath.toString(), rootPath.isAbsolute(), rootPath.getRoot());
    }
  }

  private static void normalizeResolve() {
    Path path1 = Paths.get("reports", ".", "2015", "jan");
    System.out.println(path1);
    System.out.println(path1.normalize());
    path1 = Paths.get("reports", "2015", "..", "jan");
    System.out.println(path1.normalize());
    System.out.println();
    path1 = Paths.get("reports", "2015", "jan");
    System.out.println(path1);
    System.out.println(path1.relativize(Paths.get("reports", "2016",
        "mar")));
    try {
      Path root = FileSystems.getDefault().getRootDirectories()
          .iterator().next();
      if (root != null) {
        System.out.printf("Root: %s%n", root.toString());
        Path path = Paths.get(root.toString(), "reports", "2016",
            "mar");
        System.out.printf("Path: %s%n", path);
        System.out.println(path1.relativize(path));
      }
    } catch (IllegalArgumentException iae) {
      iae.printStackTrace();
    }
    System.out.println();
    path1 = Paths.get("reports", "2015");
    System.out.println(path1);
    System.out.println(path1.resolve("apr"));
    System.out.println();
    Path path2 = Paths.get("reports", "2015", "jan");
    System.out.println(path2);
    System.out.println(path2.getParent());
    System.out.println(path2.resolveSibling(Paths.get("mar")));
    System.out.println(path2.resolve(Paths.get("mar")));
  }

  private static void additionalCapabilities() throws IOException {
    Path path1 = Paths.get("a", "b", "c");
    Path path2 = Paths.get("a", "b", "c", "d");
    System.out.printf("path1: %s%n", path1.toString());
    System.out.printf("path2: %s%n", path2.toString());
    System.out.printf("path1.equals(path2): %b%n", path1.equals(path2));
    System.out.printf("path1.equals(path2.subpath(0, 3)): %b%n",
        path1.equals(path2.subpath(0, 3)));
    System.out.printf("path1.compareTo(path2): %d%n",
        path1.compareTo(path2));
    System.out.printf("path1.startsWith(\"x\"): %b%n",
        path1.startsWith("x"));
    System.out.printf("path1.startsWith(Paths.get(\"a\"): %b%n",
        path1.startsWith(Paths.get("a")));
    System.out.printf("path2.endsWith(\"d\"): %b%n",
        path2.startsWith("d"));
    System.out.printf("path2.endsWith(Paths.get(\"c\", \"d\"): " +
            "%b%n",
        path2.endsWith(Paths.get("c", "d")));
    System.out.printf("path2.toUri(): %s%n", path2.toUri());
    Path path3 = Paths.get(".");
    System.out.printf("path3: %s%n", path3.toString());
    System.out.printf("path3.toRealPath(): %s%n", path3.toRealPath());
  }
}