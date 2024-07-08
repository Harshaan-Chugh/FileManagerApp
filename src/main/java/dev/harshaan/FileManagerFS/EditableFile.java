package dev.harshaan.FileManagerFS;

import lombok.Getter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents an editable file with functionalities to read, write, and count words and characters.
 */
public class EditableFile {
   private final String filePath;
   @Getter
   private final String fileName;
   @Getter
   private int wordCount;
   @Getter
   private int charCount;

   /**
    * Constructs an EditableFile with the specified file path.
    *
    * @param filePath the path of the file
    */
   public EditableFile(String filePath) {
      this.filePath = filePath;
      this.fileName = new File(filePath).getName();
      createFileIfNotExists();
      updateBothCounts();
   }

   /**
    * Creates the file if it does not exist.
    */
   @SuppressWarnings("ResultOfMethodCallIgnored")
   private void createFileIfNotExists() {
      File file = new File(filePath);
      try {
         if (!file.exists()) {
            file.createNewFile();
         }
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Appends the specified content to the end of the file.
    * Updates wordCount and charCount accordingly.
    *
    * @param contents The content to be written to the file.
    */
   public void write(String contents) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
         writer.write(contents);
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      updateBothCounts();
   }

   /**
    * Gets the content of the file.
    *
    * @return the content of the file
    * @throws IOException if an I/O error occurs
    */
   public String getContent() throws IOException {
      return Files.readString(Paths.get(filePath));
   }

   /**
    * Checks if the file contains the specified keyword.
    *
    * @param keyword the keyword to search for
    * @return true if the file contains the keyword, false otherwise
    * @throws IOException if an I/O error occurs
    */
   public boolean hasKeyword(String keyword) throws IOException {
      return getContent().contains(keyword);
   }

   /**
    * Updates the word count and character count of the file.
    */
   private void updateBothCounts() {
      try {
         String content = getContent();
         charCount = content.length(); // No need to subtract 1
         if (content.isEmpty()) {
            wordCount = 0;
         } else {
            wordCount = content.split("\\s+").length;
         }
      }
      catch (IOException e) {
         wordCount = 0;
         charCount = 0;
         e.printStackTrace();
      }
   }
}