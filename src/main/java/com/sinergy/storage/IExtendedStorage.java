package com.sinergy.storage;

import java.util.Set;

public interface IExtendedStorage {

    /**
     * By correct word by misspelled one
     * @param misspelledWord
     * @return
     *      correct word if exist, null otherwise
     */
    String getCorrectWord(String misspelledWord);

    void addCorrectWord(String word, String country);

    void addPair(String correctWord, String misspelledWord, String country);

    Set<String> getCorrectValues();

    Set<String> getMisspelledValues();

    boolean isCorrect(String word);

}
