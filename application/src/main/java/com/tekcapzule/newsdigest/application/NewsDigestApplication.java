package com.tekcapzule.newsdigest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapzule.newsdigest","com.tekcapzule.core"})
public class NewsDigestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsDigestApplication.class, args);
    }
}
