package dev.harshaan.FileManagerFS;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WordCounterService {
    private final MultithreadedWordCounter wordCounter = new MultithreadedWordCounter();

    public List<Map.Entry<String, Integer>> countWords(String fileName, int numThreads) {
        return wordCounter.countWords(fileName, numThreads);
    }
}
