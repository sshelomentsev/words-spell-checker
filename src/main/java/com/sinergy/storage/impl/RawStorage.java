package com.sinergy.storage.impl;

import com.sinergy.storage.IRawStorage;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class RawStorage implements IRawStorage {

    private final List<String> trainSet;
    private final List<String> testSet;

    public RawStorage() {
        this.trainSet = new LinkedList<>();
        this.testSet = new LinkedList<>();
    }

    @Override
    public Collection<String> getTrainSet() {
        return trainSet;
    }

    @Override
    public Collection<String> getTestSet() {
        return testSet;
    }

    @Override
    public void readStorage(String trainSetPath, String testSetPath) throws IOException {
        trainSet.clear();
        testSet.clear();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainSetPath)))) {
            reader.lines().forEach(trainSet::add);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(testSetPath)))) {
            reader.lines().forEach(testSet::add);
        }
    }


}
