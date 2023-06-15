package com.tekcapsule.newsdigest.domain.model;

import lombok.*;

@AllArgsConstructor
public enum Category {
    NEWS_LETTER("News Letter"),
    PODCAST("Podcast");

    @Getter
    private String value;
}