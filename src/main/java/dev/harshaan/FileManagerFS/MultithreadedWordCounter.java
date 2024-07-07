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

public class MultithreadedWordCounter {
    private static final Logger logger = Logger.getLogger(MultithreadedWordCounter.class.getName());

    public List<Map.Entry<String, Integer>> countWords(String fileName, int numThreads) {
        ConcurrentHashMap<String, Integer> wordCounts = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String finalLine = line;
                executor.submit(() -> countWordsInLine(finalLine, wordCounts));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read the file: " + fileName, e);
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        return getTopWords(wordCounts);
    }

    private void countWordsInLine(String line, ConcurrentHashMap<String, Integer> wordCounts) {
        line = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.merge(word, 1, Integer::sum);
            }
        }
    }

    private List<Map.Entry<String, Integer>> getTopWords(ConcurrentHashMap<String, Integer> wordCounts) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCounts.entrySet());

        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return entryList.subList(0, Math.min(10, entryList.size()));
    }
}
