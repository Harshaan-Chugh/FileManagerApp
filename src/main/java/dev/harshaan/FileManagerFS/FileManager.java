package dev.harshaan.FileManagerFS;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.logging.Logger;

@Getter
@RestController
@RequestMapping("/api/files")
public class FileManager {
    private static final Logger logger = Logger.getLogger(FileManager.class.getName());
    private final LinkedList<EditableFile> files;

    public FileManager() {
        this.files = new LinkedList<>();
    }
}