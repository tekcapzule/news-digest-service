package com.tekcapsule.newsdigest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.topic","com.tekcapsule.core"})
public class NewsDigestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsDigestApplication.class, args);
    }
}
