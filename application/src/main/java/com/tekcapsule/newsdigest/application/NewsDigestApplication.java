package com.tekcapsule.newsdigest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.newsdigest","com.tekcapsule.core"})
public class NewsDigestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsDigestApplication.class, args);
    }
}
