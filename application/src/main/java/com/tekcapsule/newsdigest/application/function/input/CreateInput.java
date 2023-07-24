package com.tekcapsule.newsdigest.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.newsdigest.domain.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String title;
    private Category category;
    private String author;
    private String summary;
    private String description;
    private String schedule;
    private String imageUrl;
    private String resourceUrl;
    private int recommendations;

}