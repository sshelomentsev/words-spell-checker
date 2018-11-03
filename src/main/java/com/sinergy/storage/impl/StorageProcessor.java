package com.sinergy.storage.impl;

import com.sinergy.storage.IExtendedStorage;
import com.sinergy.storage.IRawStorage;
import com.sinergy.storage.IStorageProcessor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StorageProcessor implements IStorageProcessor {

    @Autowired
    @Qualifier("surnamesStorage")
    private IExtendedStorage surnamesStorage;

    @Autowired
    @Qualifier("namesStorage")
    private IExtendedStorage namesStorage;

    @Autowired
    @Qualifier("thirdNamesStorage")
    private IExtendedStorage thirdNamesStorage;

    @Autowired
    private IRawStorage rawStorage;

    @Autowired
    private DictionaryStorage dictionaryStorage;

    @Override
    public void load() {
        readTrainSet();
        filterDictionaryWords();
    }

    private void filterDictionaryWords() {
        surnamesStorage.getCorrectValues().forEach(value -> dictionaryStorage.removeWord(value));
        surnamesStorage.getMisspelledValues().forEach(value -> dictionaryStorage.removeWord(value));
        namesStorage.getCorrectValues().forEach(value -> dictionaryStorage.removeWord(value));
        namesStorage.getMisspelledValues().forEach(value -> dictionaryStorage.removeWord(value));
        thirdNamesStorage.getCorrectValues().forEach(value -> dictionaryStorage.removeWord(value));
        thirdNamesStorage.getMisspelledValues().forEach(value -> dictionaryStorage.removeWord(value));
    }

    private void readTrainSet() {
        rawStorage.getTrainSet().forEach(line -> {
            Pair<Integer, Integer> pair = getOrdinal(line);

            String country = getCountry(line, pair.getValue1());

            String[] nameParts = line.split(",")[1].toUpperCase()
                    .trim().replaceAll("\\s+", " ").split("\\s");
            String givenSurname = nameParts[0];
            String givenName = nameParts.length > 1 ? nameParts[1] : null;
            String givenThirdName = null;
            if (nameParts.length > 2) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < nameParts.length; i++) {
                    sb.append(nameParts[i]);
                    if (i != nameParts.length - 1) {
                        sb.append(" ");
                    }
                }
                givenThirdName = sb.toString();
            }

            if (0 == pair.getValue0()) { //correct example
                surnamesStorage.addCorrectWord(givenSurname, country);
                if (null != givenName) {
                    namesStorage.addCorrectWord(givenName, country);
                }
                if (null != givenSurname) {
                    thirdNamesStorage.addCorrectWord(givenThirdName, country);
                }
            } else if (1 == pair.getValue0()) { //misspelled example
                String[] correctedNameParts = line.split(",")[pair.getValue1() + 1].toUpperCase()
                        .trim().replaceAll("\\s+", " ").split("\\s");
                String correctedSurname = correctedNameParts[0];
                String correctedName = correctedNameParts.length > 1 ? correctedNameParts[1] : null;
                String correctedThirdName = null;
                if (correctedNameParts.length > 2) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < correctedNameParts.length; i++) {
                        sb.append(correctedNameParts[i]);
                        if (i != correctedNameParts.length - 1) {
                            sb.append(" ");
                        }
                    }
                    correctedThirdName = sb.toString();
                }

                surnamesStorage.addCorrectWord(correctedSurname, country);
                if (!correctedSurname.equals(givenSurname)) {
                    surnamesStorage.addPair(correctedSurname, givenSurname, country);
                }

                if (null != correctedName) {
                    if (null != givenName && !correctedName.equals(givenName)) {
                        namesStorage.addCorrectWord(correctedName, country);
                        namesStorage.addPair(correctedName, givenName, country);
                    }
                }

                if (null != correctedThirdName) {
                    if (null != givenThirdName && !correctedThirdName.equals(givenThirdName)) {
                        thirdNamesStorage.addCorrectWord(givenThirdName, country);
                        thirdNamesStorage.addPair(correctedThirdName, givenThirdName, country);
                    }
                }
            } else if (2 == pair.getValue0()) { //incorrect example
                dictionaryStorage.addWord(givenName);
                dictionaryStorage.addWord(givenSurname);
                dictionaryStorage.addWord(givenThirdName);
            }
        });
    }

    private String getCountry(String line, int position) {
        int start = 2;
        String[] parts = line.split(",");
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < position; i++) {
            sb.append(parts[i]);
            if (i != position - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private Pair<Integer, Integer> getOrdinal(String line) {
        String[] parts = line.split(",");
        int ordinal = -1;
        int position = 3;

        try {
            ordinal = Integer.parseInt(parts[3]);
        } catch (NumberFormatException e) {
            int ind = line.indexOf(",");

            String[] px = line.substring(ind).split(",");
            for (int i = 0; i < px.length; i++) {
                ordinal = parse(px[i]);
                if (3 != ordinal) {
                    position = i + 1;
                    break;
                }
            }
        }
        return new Pair(ordinal, position);
    }

    private int parse(String v) {
        int ret;
        try {
            ret = Integer.parseInt(v);
        } catch (NumberFormatException e) {
            ret = -1;
        }
        return ret;
    }
}
