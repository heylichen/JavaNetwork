package tcpip.nio.nio2.filesys.filesystemprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;

import static java.nio.file.Files.getFileAttributeView;

/**
 * Created by Chen Li on 2017/3/11.
 */
public class FileAttributeViewDemo {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileAttributeViewDemo.class);

  public static void main(String[] args) throws IOException {
//    supportedFileAttributeViews();
//    bulkReadFileAttributes();
//    setAndGetSingleAttribute();
    exlporeFileOwnerView();
  }

  private static void supportedFileAttributeViews() {
    FileSystem fsDefault = FileSystems.getDefault();
    for (String view : fsDefault.supportedFileAttributeViews())
      System.out.println(view);

    System.out.printf("Supports basic: %b%n",
        isSupported(BasicFileAttributeView.class));
    System.out.printf("Supports posix: %b%n",
        isSupported(PosixFileAttributeView.class));
    System.out.printf("Supports acl: %b%n",
        isSupported(AclFileAttributeView.class));
  }

  private static boolean isSupported(Class<? extends FileAttributeView> clazz) {
    return getFileAttributeView(Paths.get("."), clazz) != null;
  }


  private static void bulkReadFileAttributes() throws IOException {
    FileSystem fsDefault = FileSystems.getDefault();
    Path currentPath = Paths.get(".");
    BasicFileAttributeView basicFav = Files.getFileAttributeView(currentPath, BasicFileAttributeView.class);

    BasicFileAttributes fileAttributes = basicFav.readAttributes();
    LOGGER.info("path: {}", currentPath.toAbsolutePath());
    LOGGER.info("creationTime: {}", fileAttributes.creationTime());
    LOGGER.info("lastAccessTime: {}", fileAttributes.lastAccessTime());
    LOGGER.info("lastModifiedTime: {}", fileAttributes.lastModifiedTime());

    LOGGER.info("isDirectory: {}", fileAttributes.isDirectory());
    LOGGER.info("isRegularFile: {}", fileAttributes.isRegularFile());
    LOGGER.info("isOther: {}", fileAttributes.isOther());
    LOGGER.info("isSymbolicLink: {}", fileAttributes.isSymbolicLink());

    LOGGER.info("fileKey: {}", fileAttributes.fileKey());
  }

  private static final String CREATE_TIME = "creationTime";

  private static void setAndGetSingleAttribute() throws IOException {
    Path currentPath = Paths.get(".");
    Object creationTime = Files.getAttribute(currentPath, CREATE_TIME);
    LOGGER.info("path: {}", currentPath.toAbsolutePath());
    LOGGER.info("creationTime: {}", creationTime);
  }

  private static void exlporeFileOwnerView() throws IOException {
    Path path = Paths.get(".");
    System.out.printf("Owner: %s%n", Files.getOwner(path));
    UserPrincipal up = path.getFileSystem().
        getUserPrincipalLookupService().
        lookupPrincipalByName("jeff");
    System.out.println(up);
    Files.setOwner(path, up);
    System.out.printf("Owner: %s%n", Files.getOwner(path));
  }
}
