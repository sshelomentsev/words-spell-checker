package com.sinergy.storage;

public interface IDictionaryStorage {

    void addWord(String word);

    boolean hasWord(String word);

    void removeWord(String word);
}
