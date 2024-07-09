package dev.harshaan.FileManagerFS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing file operations.
 */
@RestController
@RequestMapping("/api/files")
public class FileManagerController {
    @Autowired
    private FileManagerService fileManagerService;

    /**
     * Loads files from a specified directory.
     *
     * @param directoryPath the path of the directory to load files from
     * @return a list of file details, including file name, word count, and character count
     */
    @GetMapping("/loadFiles")
    public ResponseEntity<List<Map<String, Object>>> loadFilesFromDirectory(@RequestParam String directoryPath) {
        try {
            List<Map<String, Object>> files = fileManagerService.loadFilesFromDirectory(directoryPath);
            return ResponseEntity.ok(files);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Creates a new file with the specified content.
     *
     * @param fileName    the name of the file to create
     * @param fileContent the content to write to the file
     * @return a response indicating success or failure
     */
    @PostMapping("/createFile")
    public ResponseEntity<String> createFile(@RequestParam String fileName, @RequestParam String fileContent) {
        try {
            fileManagerService.createFile(fileName, fileContent);
            return ResponseEntity.ok("File created successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Writes content to an existing file.
     *
     * @param fileName    the name of the file to write to
     * @param fileContent the content to write to the file
     * @return a response indicating success or failure
     */
    @PostMapping("/writeFile")
    public ResponseEntity<String> writeFile(@RequestParam String fileName, @RequestParam String fileContent) {
        try {
            fileManagerService.writeFile(fileName, fileContent);
            return ResponseEntity.ok("File written successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Deletes a file.
     *
     * @param fileName the name of the file to delete
     * @return a response indicating success or failure
     */
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        try {
            fileManagerService.deleteFile(fileName);
            return ResponseEntity.ok("File deleted successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }

    /**
     * Deletes duplicate files based on content.
     *
     * @return a response indicating success or failure
     */
    @DeleteMapping("/deleteDuplicates")
    public ResponseEntity<String> deleteDuplicates() {
        try {
            fileManagerService.deleteDuplicates();
            return ResponseEntity.ok("Duplicate files deleted successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting duplicates: " + e.getMessage());
        }
    }

    /**
     * Searches for files containing the specified keyword.
     *
     * @param keyword the keyword to search for
     * @return a list of file names containing the keyword
     */
    @GetMapping("/keywordSearch")
    public ResponseEntity<List<String>> keywordSearch(@RequestParam String keyword) {
        try {
            List<String> results = fileManagerService.keywordSearch(keyword);
            return ResponseEntity.ok(results);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Counts words in a file using the specified number of threads.
     *
     * @param fileName   the name of the file to count words in
     * @param numThreads the number of threads to use
     * @return a list of word counts
     */
    @GetMapping("/countWords")
    public ResponseEntity<List<String>> countWords(@RequestParam String fileName, @RequestParam int numThreads) {
        try {
            List<String> results = fileManagerService.countWords(fileName, numThreads);
            return ResponseEntity.ok(results);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}