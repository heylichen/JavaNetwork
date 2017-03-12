package tcpip.nio.nio2.filesys.filesystemprovider;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by Chen Li on 2017/3/11.
 */
public class FileStoreDemo {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("usage: java FSDemo path");
      return;
    }
    FileStore fs = Files.getFileStore(Paths.get(args[0]));
    System.out.printf("Total space: %d%n", fs.getTotalSpace());
    System.out.printf("Unallocated space: %d%n",
        fs.getUnallocatedSpace());
    System.out.printf("Usable space: %d%n",
        fs.getUsableSpace());
    System.out.printf("Read only: %b%n", fs.isReadOnly());
    System.out.printf("Name: %s%n", fs.name());
    System.out.printf("Type: %s%n%n", fs.type());


    FileSystem fsDefault = FileSystems.getDefault();
    for (FileStore fileStore : fsDefault.getFileStores()) {
      System.out.printf("Filestore: %s%n", fileStore);
    }

  }
}
