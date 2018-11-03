package com.sinergy.wordsspellchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordsSpellCheckerApplication {

	public static void main(String[] args) {
		final Class<?>[] runner = new Class<?>[] { TestRunner.class };
		System.exit(SpringApplication.exit(SpringApplication.run(runner, args)));
	}
}
