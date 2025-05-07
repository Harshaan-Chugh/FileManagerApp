package dev.harshaan.FileScout;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Controller for managing files.
 */
@Getter
@RestController
@RequestMapping("/api/files")
public class FileScout {
    private static final Logger logger = Logger.getLogger(FileScout.class.getName());
    private final LinkedList<EditableFile> files;

    public FileScout() {
        this.files = new LinkedList<>();
    }
}