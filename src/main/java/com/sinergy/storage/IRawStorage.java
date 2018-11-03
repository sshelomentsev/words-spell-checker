package com.sinergy.storage;

import java.io.IOException;
import java.util.Collection;

public interface IRawStorage {

    Collection<String> getTrainSet();

    Collection<String> getTestSet();

    void readStorage(String trainSetPath, String testSetPath) throws IOException;

}
