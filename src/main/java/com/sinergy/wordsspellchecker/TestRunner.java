package com.sinergy.wordsspellchecker;

import com.sinergy.storage.IExtendedStorage;
import com.sinergy.storage.IRawStorage;
import com.sinergy.storage.IStorageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.sinergy")
public class TestRunner implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private IRawStorage rawStorage;

    @Autowired
    private IStorageProcessor storageProcessor;

    @Autowired
    @Qualifier("surnamesStorage")
    private IExtendedStorage surnamesStorage;

    @Autowired
    @Qualifier("namesStorage")
    private IExtendedStorage namesStorage;

    @Autowired
    @Qualifier("thirdNamesStorage")
    private IExtendedStorage thirdNamesStorage;

    @Override
    public void run(String... args) throws Exception {

        rawStorage.readStorage(appConfig.getTrainSet(), appConfig.getTestSet());

        storageProcessor.load();
    }

}
