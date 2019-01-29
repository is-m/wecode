package com.chinasoft.it.wecode.common.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zip工具类
 * @see https://blog.csdn.net/j16421881/article/details/78858690
 * @author Administrator
 *
 */
public class ZipUtils {

  /**
   * Returns a zip file system
   *
   * @param zipFilename to construct the file system from
   * @param create true if the zip file should be created
   * @return a zip file system
   */
  private static FileSystem createZipFileSystem(String zipFilename, boolean create) throws IOException {
    // convert the filename to a URI
    final Path path = Paths.get(zipFilename);
    final URI uri = URI.create("jar:file:" + path.toUri().getPath());

    final Map<String, String> env = new HashMap<>();
    if (create) {
      env.put("create", "true");
    }
    return FileSystems.newFileSystem(uri, env);
  }

  /**
   * Unzips the specified zip file to the specified destination directory.
   * Replaces any files in the destination, if they already exist.
   *
   * @param zipFilename the name of the zip file to extract
   * @param destDirname the directory to unzip to
   */
  public static void unzip(String zipFilename, String destDirname) throws IOException {

    final Path destDir = Paths.get(destDirname);
    // if the destination doesn't exist, create it
    if (Files.notExists(destDir)) {
      System.out.println(destDir + " does not exist. Creating...");
      Files.createDirectories(destDir);
    }

    try (FileSystem zipFileSystem = createZipFileSystem(zipFilename, false)) {
      final Path root = zipFileSystem.getPath("/");

      // walk the zip file tree and copy files to the destination
      Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          final Path destFile = Paths.get(destDir.toString(), file.toString());
          System.out.printf("Extracting file %s to %s\n", file, destFile);
          Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          final Path dirToCreate = Paths.get(destDir.toString(), dir.toString());
          if (Files.notExists(dirToCreate)) {
            System.out.printf("Creating directory %s\n", dirToCreate);
            Files.createDirectory(dirToCreate);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    }
  }

  /**
   * Creates/updates a zip file.
   *
   * @param zipFilename the name of the zip to create
   * @param filenames list of filename to add to the zip
   */
  public static void create(String zipFilename, String... filenames) throws IOException {

    try (FileSystem zipFileSystem = createZipFileSystem(zipFilename, true)) {
      final Path root = zipFileSystem.getPath("/");

      // iterate over the files we need to add
      for (String filename : filenames) {
        final Path src = Paths.get(filename);

        // add a file to the zip file system
        if (!Files.isDirectory(src)) {
          final Path dest = zipFileSystem.getPath(root.toString(), src.toString());
          final Path parent = dest.getParent();
          if (Files.notExists(parent)) {
            System.out.printf("Creating directory %s\n", parent);
            Files.createDirectories(parent);
          }
          Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } else {
          // for directories, walk the file tree
          Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
              final Path dest = zipFileSystem.getPath(root.toString(), file.toString());
              Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
              final Path dirToCreate = zipFileSystem.getPath(root.toString(), dir.toString());
              if (Files.notExists(dirToCreate)) {
                System.out.printf("Creating directory %s\n", dirToCreate);
                Files.createDirectories(dirToCreate);
              }
              return FileVisitResult.CONTINUE;
            }
          });
        }
      }
    }
  }

  /**
   * List the contents of the specified zip file
   */
  public static void list(String zipFilename) throws IOException {

    System.out.printf("Listing Archive:  %s\n", zipFilename);

    // create the file system
    try (FileSystem zipFileSystem = createZipFileSystem(zipFilename, false)) {

      final Path root = zipFileSystem.getPath("/");

      // walk the file tree and print out the directory and filenames
      Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          print(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          print(dir);
          return FileVisitResult.CONTINUE;
        }

        /**
         * prints out details about the specified path
         * such as size and modification time
         * @param file
         * @throws IOException
         */
        private void print(Path file) throws IOException {
          final DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
          final String modTime = df.format(new Date(Files.getLastModifiedTime(file).toMillis()));
          System.out.printf("%d  %s  %s\n", Files.size(file), modTime, file);
        }
      });
    }
  }

  /**
   * 读取文件
   * @param zipFilePath
   * @param readFilePath
   * @return
   * @throws IOException 
   */
  public static byte[] readFileBytes(String zipFilePath, String readFilePath) throws IOException {
    try (FileSystem zipFileSystem = createZipFileSystem(zipFilePath, false)) {
      Path path = zipFileSystem.getPath(readFilePath);
      return Files.readAllBytes(path);
    }
  }

  public static List<String> readFileLines(String zipFilePath, String readFilePath) throws IOException {
    try (FileSystem zipFileSystem = createZipFileSystem(zipFilePath, false)) {
      Path path = zipFileSystem.getPath(readFilePath);
      return Files.readAllLines(path);
    }
  }

  /**
   * 创建或替换文件
   * @param zipFilePath
   * @param writeFilePath
   */
  public void createOrReplace(String zipFilePath, String writeFilePath) {

  }

  /**
   * 创建或替换文件
   * @param zipFilePath
   * @param writeFilePath
   * @param fileContent
   */
  public void createOrReplaceFile(String zipFilePath, String writeFilePath, byte[] fileContent) {

  }
}
