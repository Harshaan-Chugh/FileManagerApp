package dev.harshaan.FileScout;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * Controller for managing files.
 */
@Getter
@RestController
@RequestMapping("/api/files")
public class FileScout {
    private final LinkedList<EditableFile> files;

    public FileScout() {
        this.files = new LinkedList<>();
    }
}