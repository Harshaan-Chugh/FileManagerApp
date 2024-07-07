package dev.harshaan.FileManagerFS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileManagerController {
    @Autowired
    private FileManagerService fileManagerService;

    @GetMapping("/loadFiles")
    public ResponseEntity<List<String>> loadFilesFromDirectory(@RequestParam String directoryPath) {
        try {
            List<String> files = fileManagerService.loadFilesFromDirectory(directoryPath);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/createFile")
    public ResponseEntity<String> createFile(@RequestParam String fileName, @RequestParam String fileContent) {
        try {
            fileManagerService.createFile(fileName, fileContent);
            return ResponseEntity.ok("File created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating file: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        try {
            fileManagerService.deleteFile(fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteDuplicates")
    public ResponseEntity<String> deleteDuplicates() {
        try {
            fileManagerService.deleteDuplicates();
            return ResponseEntity.ok("Duplicate files deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting duplicates: " + e.getMessage());
        }
    }

    @GetMapping("/keywordSearch")
    public ResponseEntity<List<String>> keywordSearch(@RequestParam String keyword) {
        try {
            List<String> results = fileManagerService.keywordSearch(keyword);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/countWords")
    public ResponseEntity<List<String>> countWords(@RequestParam String fileName, @RequestParam int numThreads) {
        try {
            List<String> results = fileManagerService.countWords(fileName, numThreads);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
