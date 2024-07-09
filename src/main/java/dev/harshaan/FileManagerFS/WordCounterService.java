package dev.harshaan.FileManagerFS;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service for counting words and returning the top 10 words in a file.
 * Uses a multithreaded approach to count words.
 */
@Service
public class WordCounterService {
    private final MultithreadedWordCounter wordCounter = new MultithreadedWordCounter();

    public List<Map.Entry<String, Integer>> countWords(String fileName, int numThreads) {
        return wordCounter.countWords(fileName, numThreads);
    }
}