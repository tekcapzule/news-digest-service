package com.tekcapsule.newsdigest.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.newsdigest.domain.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
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