package com.sinergy.storage.impl;

import com.sinergy.storage.IDictionaryStorage;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DictionaryStorage implements IDictionaryStorage {

    private Set<String> words = new HashSet<>();

    @Override
    public void addWord(String word) {
        words.add(word);
    }

    @Override
    public boolean hasWord(String word) {
        return words.contains(word);
    }

    @Override
    public void removeWord(String word) {
        words.remove(word);
    }


}
