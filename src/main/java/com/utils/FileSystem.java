package com.utils;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * utils for file system operations.
 */
@Slf4j
public class FileSystem {

    /**
     * unzip file
     * 
     * @param source      source
     * @param destination destination
     */
    private void unzip(String source, String destination) {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
        }
    }

    /**
     * Delete file path.
     *
     * @param path Path to file for folder.
     * @throws IOException When fail to delete it.
     */
    public void deletePath(String path) throws IOException {
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                file.delete();
            }
        } catch (Exception e) {
            String errorMessage = "Failed to delete " + path;
            throw new IOException(errorMessage);
        }
    }

    /**
     * Read content of file.
     *
     * @param filePath File path as String.
     * @return Content of file as String.
     * @throws IOException When fail to read file.
     */
    public String readFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, Charset.defaultCharset());
    }

    /**
     * Check if path exists.
     *
     * @param path Path as String.
     * @return True if path exists. False if path does not exist.
     */
    public static boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Ensure path exists (create if does not exists).
     *
     * @param directory Path to directory.
     */
    public static void ensureFolderExists(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
            }
        }
    }

    /**
     * Get size of file.
     *
     * @param path Path to file.
     * @return Size of file in kB.
     */
    public long getFileSize(String path) {
        File file;
        long size = 0;
        file = new File(path);
        if (file.exists()) {
            size = file.length() / 1024; // In KBs
        } else {
            Assert.fail("File '" + file + "' does not exist!");
        }
        return size;
    }
}