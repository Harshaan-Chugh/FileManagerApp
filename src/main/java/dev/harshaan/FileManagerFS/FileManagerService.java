package dev.harshaan.FileManagerFS;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class FileManagerService {
    private static final List<String> TEXT_FILE_EXTENSIONS = Arrays.asList("txt", "md");
    private final WordCounterService wordCounterService = new WordCounterService();
    private String directoryPath;

    public List<String> loadFilesFromDirectory(String directoryPath) {
        this.directoryPath = directoryPath;
        File directory = new File(directoryPath);
        List<String> fileNames = new ArrayList<>();
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isTextFile(file)) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    private boolean isTextFile(File file) {
        String fileName = file.getName().toLowerCase();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1);
            return TEXT_FILE_EXTENSIONS.contains(extension);
        }
        return false;
    }

    public void createFile(String fileName, String fileContent) {
        new EditableFile(fileName, fileContent);
    }

    public void deleteFile(String fileName) throws Exception {
        EditableFile editableFile = new EditableFile(fileName);
        File file = new File(editableFile.getFilePath());
        if (!file.exists() || !file.isFile()) {
            throw new Exception("File not found: " + fileName);
        }
        if (!file.delete()) {
            throw new Exception("Failed to delete file: " + fileName);
        }
    }

    public void deleteDuplicates() throws Exception {
        if (directoryPath == null) {
            throw new Exception("Directory path not set");
        }

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
                } else {
                    uniqueFiles.put(fileContent, editableFile);
                }
            }
        }
    }

    public List<String> keywordSearch(String keyword) throws Exception {
        if (directoryPath == null) {
            throw new Exception("Directory path not set");
        }

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

    public List<String> countWords(String fileName, int numThreads) {
        List<Map.Entry<String, Integer>> wordCounts = wordCounterService.countWords(fileName, numThreads);
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts) {
            result.add(entry.getKey() + ": " + entry.getValue());
        }
        return result;
    }
}