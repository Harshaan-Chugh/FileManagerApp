package dev.harshaan.FileManagerFS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Multithreaded word counter that counts words in a file using a specified number of threads.
 */
public class MultithreadedWordCounter {
    private static final Logger logger = Logger.getLogger(MultithreadedWordCounter.class.getName());

    /**
     * Counts words in the specified file using the specified number of threads.
     *
     * @param fileName   the name of the file to count words in
     * @param numThreads the number of threads to use
     * @return a list of word counts
     */
    public List<Map.Entry<String, Integer>> countWords(String fileName, int numThreads) {
        ConcurrentHashMap<String, Integer> wordCounts = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String finalLine = line;
                executor.submit(() -> countWordsInLine(finalLine, wordCounts));
            }
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read the file: " + fileName, e);
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executor.shutdownNow();
        }

        return getTopWords(wordCounts);
    }

    /**
     * Counts words in the specified line and updates the word counts map.
     *
     * @param line       the line to count words in
     * @param wordCounts the map to update with word counts
     */
    private void countWordsInLine(String line, ConcurrentHashMap<String, Integer> wordCounts) {
        line = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.merge(word, 1, Integer::sum);
            }
        }
    }

    /**
     * Gets the top words and their counts from the word counts map.
     *
     * @param wordCounts the map of word counts
     * @return a list of the top words and their counts
     */
    private List<Map.Entry<String, Integer>> getTopWords(ConcurrentHashMap<String, Integer> wordCounts) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCounts.entrySet());

        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return entryList.subList(0, Math.min(10, entryList.size()));
    }
}