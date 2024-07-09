package dev.harshaan.FileManagerFS;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Service for managing files in a specified directory.
 */
@Service
public class FileManagerService {
    private static final List<String> TEXT_FILE_EXTENSIONS = Arrays.asList("txt", "md");
    private final WordCounterService wordCounterService = new WordCounterService();
    private String directoryPath;

    /**
     * Loads text files from the specified directory.
     *
     * @param directoryPath the path of the directory to load files from
     * @return a list of file details, including file name, word count, and character count
     */
    public List<Map<String, Object>> loadFilesFromDirectory(String directoryPath) {
        try {
            String decodedPath = URLDecoder.decode(directoryPath, StandardCharsets.UTF_8);
            this.directoryPath = decodedPath;
            File directory = new File(decodedPath);
            List<Map<String, Object>> fileDetailsList = new ArrayList<>();
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (isTextFile(file)) {
                            Map<String, Object> fileDetails = new HashMap<>();
                            EditableFile editableFile = new EditableFile(file.getName());
                            fileDetails.put("fileName", file.getName());
                            fileDetails.put("wordCount", editableFile.getWordCount());
                            fileDetails.put("charCount", editableFile.getCharCount());
                            fileDetailsList.add(fileDetails);
                        }
                    }
                }
            }
            return fileDetailsList;
        }
        catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Checks if a file is a text file based on its extension.
     *
     * @param file the file to check
     * @return true if the file is a text file, false otherwise
     */
    private boolean isTextFile(File file) {
        String fileName = file.getName().toLowerCase();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1);
            return TEXT_FILE_EXTENSIONS.contains(extension);
        }
        return false;
    }

    /**
     * Checks if the directory path is set.
     *
     * @throws Exception if the directory path is not set
     */
    private void checkDirectorySet() throws Exception {
        if (directoryPath == null) {
            throw new Exception("Directory path not set");
        }
    }

    /**
     * Creates a new file with the specified content in the set directory.
     *
     * @param fileName    the name of the file to create
     * @param fileContent the content to write to the file
     * @throws Exception if the directory path is not set or an error occurs during file creation
     */
    public void createFile(String fileName, String fileContent) throws Exception {
        checkDirectorySet();
        File file = new File(directoryPath, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(fileContent);
        }
    }

    /**
     * Writes content to an existing file in the set directory.
     *
     * @param fileName    the name of the file to write to
     * @param fileContent the content to write to the file
     * @throws Exception if the directory path is not set or an error occurs during file writing
     */
    public void writeFile(String fileName, String fileContent) throws Exception {
        checkDirectorySet();
        EditableFile editableFile = new EditableFile(directoryPath + File.separator + fileName);
        editableFile.write(fileContent);
    }

    /**
     * Deletes a file in the set directory.
     *
     * @param fileName the name of the file to delete
     * @throws Exception if the directory path is not set or an error occurs during file deletion
     */
    public void deleteFile(String fileName) throws Exception {
        checkDirectorySet();
        File file = new File(directoryPath, fileName);
        if (!file.exists() || !file.isFile()) {
            throw new Exception("File not found: " + fileName);
        }
        if (!file.delete()) {
            throw new Exception("Failed to delete file: " + fileName);
        }
    }

    /**
     * Deletes duplicate files in the set directory based on file content.
     *
     * @throws Exception if the directory path is not set or an error occurs during file deletion
     */
    public void deleteDuplicates() throws Exception {
        checkDirectorySet();
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception("Invalid directory path");
        }

        Map<String, EditableFile> uniqueFiles = new HashMap<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && isTextFile(file)) {
                EditableFile editableFile = new EditableFile(file.getName());
                String fileContent = editableFile.getContent();
                if (uniqueFiles.containsKey(fileContent)) {
                    if (!file.delete()) {
                        throw new Exception("Failed to delete duplicate file: " + file.getName());
                    }
                }
                else {
                    uniqueFiles.put(fileContent, editableFile);
                }
            }
        }
    }

    /**
     * Searches for files containing a specified keyword in the set directory.
     *
     * @param keyword the keyword to search for
     * @return a list of file names containing the keyword
     * @throws Exception if the directory path is not set or an error occurs during the search
     */
    public List<String> keywordSearch(String keyword) throws Exception {
        checkDirectorySet();
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception("Invalid directory path");
        }

        List<String> searchResults = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && isTextFile(file)) {
                EditableFile editableFile = new EditableFile(file.getName());
                if (editableFile.hasKeyword(keyword)) {
                    searchResults.add(editableFile.getFileName());
                }
            }
        }
        return searchResults;
    }

    /**
     * Counts words in a specified file using a specified number of threads.
     *
     * @param fileName   the name of the file to count words in
     * @param numThreads the number of threads to use for counting words
     * @return a list of word counts in the format "word: count"
     * @throws Exception if the directory path is not set or an error occurs during word counting
     */
    public List<String> countWords(String fileName, int numThreads) throws Exception {
        checkDirectorySet();
        List<Map.Entry<String, Integer>> wordCounts = wordCounterService.countWords(new File(directoryPath, fileName).getPath(), numThreads);
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts) {
            result.add(entry.getKey() + ": " + entry.getValue());
        }
        return result;
    }
}