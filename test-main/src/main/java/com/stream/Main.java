package com.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by alpha on 2017/5/24.
 */
@SpringBootApplication
public class Main {
    private static final String PROFILE = "stest";

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", PROFILE);
        SpringApplication.run(Main.class, args);
    }
}
