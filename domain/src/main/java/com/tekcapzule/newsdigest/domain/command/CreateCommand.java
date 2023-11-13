package com.tekcapzule.newsdigest.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import com.tekcapzule.newsdigest.domain.model.Category;
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