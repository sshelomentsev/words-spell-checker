package com.sinergy.wordsspellchecker;

import com.sinergy.storage.IExtendedStorage;
import com.sinergy.storage.impl.ExtendedStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String testSet = "/Users/sshelomentsev/Downloads/test.csv";
    private final String trainSet = "/Users/sshelomentsev/Downloads/train.csv";
    private final String resultSet = "dsfsdfds";

    @Bean
    public IExtendedStorage namesStorage() {
        return new ExtendedStorage();
    }

    @Bean
    public IExtendedStorage surnamesStorage() {
        return new ExtendedStorage();
    }

    @Bean
    public IExtendedStorage thirdNamesStorage() {
        return new ExtendedStorage();
    }

    public String getTestSet() {
        return testSet;
    }

    public String getTrainSet() {
        return trainSet;
    }

    public String getResultSet() {
        return resultSet;
    }

}
