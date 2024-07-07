package dev.harshaan.FileManagerFS;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;

/**
 * Represents a file with functionality for reading, writing, and managing content.
 * Author: Harshaan Chugh
 */
@SuppressWarnings("ALL")
public class EditableFile {
   private final String path;
   private int wordCount;
   private int charCount;
   private String fileName;

   /**
    * Constructs an EditableFile with the specified fileName in the current directory
    *
    * @param fileName The name of the file to be created or opened
    */
   public EditableFile(String fileName) {
      this.fileName = fileName;
      String currentDirectory = System.getProperty("user.dir");
      this.path = currentDirectory + File.separator + fileName;

      try {
         File file = new File(path);
         if (!file.exists()) {
            file.createNewFile();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      updateBothCounts();
   }

   /**
    * Constructs an EditableFile with the specified fileName and initial content in the current directory
    *
    * @param fileName The name of the file to be created or opened
    * @param contents The initial content of the file
    */
   public EditableFile(String fileName, String contents) {
      this.fileName = fileName;
      String currentDirectory = System.getProperty("user.dir");
      this.path = currentDirectory + File.separator + fileName;

      try {
         File file = new File(path);
         if (!file.exists()) {
            file.createNewFile();
         }
         write(contents);
      } catch (IOException e) {
         e.printStackTrace();
      }

      updateBothCounts();
   }

   /**
    * Accessor method that returns the file path.
    *
    * @return path
    */
   public String getFilePath() {
      return path;
   }

   /**
    * Appends the specified content to the end of the file. Updates wordCount and
    * charCount accordingly.
    *
    * @param contents The content to be written to the file.
    */
   public void write(String contents) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) { // Change append mode to false
         writer.write(contents);
      } catch (IOException e) {
         e.printStackTrace();
      }

      updateBothCounts();
   }

   /**
    * Reads the content of the file.
    *
    * @return The content of the file as a string
    */
   public String getContent() {
      StringBuilder content = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
         String line;
         while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return content.toString();
   }

   /**
    * Method that returns the word count of an EditableFile.
    *
    * @return wordCount
    */
   public int getWordCount() {
      return wordCount;
   }

   /**
    * Method that returns the character count of an EditableFile
    *
    * @return charCount
    */
   public int getCharCount() {
      return charCount;
   }

   /**
    * Updates both the character and word counts.
    */
   public void updateBothCounts() {
      String input = getContent();
      charCount = input.length();
      if (input.isEmpty()) {
         wordCount = 0;
      } else {
         String[] words = input.split("\\s+");
         wordCount = words.length;
      }
   }

   /**
    * Searches for a keyword in the file content.
    *
    * @param keyword The keyword to search for in the file content.
    * @return true if the keyword is found, else false.
    */
   public boolean hasKeyword(String keyword) {
      String content = getContent();
      return content.contains(keyword);
   }

   public String getFileName() {
      return fileName;
   }
}