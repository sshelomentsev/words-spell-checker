package com.sinergy.storage.impl;

import com.sinergy.storage.IExtendedStorage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ExtendedStorage implements IExtendedStorage {

    private Map<String, Integer> correctWords = new HashMap<>();
    private Map<String, String> misspelledToCorrectMap = new HashMap<>();

    private Map<String, Map<String, Integer>> correctWordsByCountries = new HashMap<>();
    private Map<String, Map<String, String>> misspelledByCountries = new HashMap<>();

    @Override
    public String getCorrectWord(String misspelledWord) {
        return misspelledToCorrectMap.get(misspelledWord);
    }

    @Override
    public void addCorrectWord(String word, String country) {
        Integer count = correctWords.getOrDefault(word, 0);
        count++;
        correctWords.put(word, count);

        Map<String, Integer> byCountry = correctWordsByCountries.getOrDefault(word, new HashMap<>());
        Integer cc = byCountry.getOrDefault(country, 0);
        cc++;
        byCountry.put(country, cc);
        correctWordsByCountries.put(word, byCountry);
    }

    @Override
    public void addPair(String correctWord, String misspelledWord, String country) {
        misspelledToCorrectMap.put(misspelledWord, correctWord);

        Map<String, String> byCountry = misspelledByCountries.getOrDefault(misspelledWord, new HashMap<>());
        byCountry.put(country, correctWord);
        misspelledByCountries.put(misspelledWord, byCountry);
    }

    @Override
    public Set<String> getCorrectValues() {
        return correctWords.keySet();
    }

    @Override
    public Set<String> getMisspelledValues() {
        return new HashSet<>(misspelledToCorrectMap.values());
    }

    @Override
    public boolean isCorrect(String word) {
        return correctWords.containsKey(word);
    }

}
