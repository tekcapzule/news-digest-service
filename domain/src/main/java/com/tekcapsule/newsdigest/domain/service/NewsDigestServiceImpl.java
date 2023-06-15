package com.tekcapsule.newsdigest.domain.service;

import com.tekcapsule.newsdigest.domain.command.CreateCommand;
import com.tekcapsule.newsdigest.domain.command.DisableCommand;
import com.tekcapsule.newsdigest.domain.command.UpdateCommand;
import com.tekcapsule.newsdigest.domain.model.Digest;
import com.tekcapsule.newsdigest.domain.repository.NewsDigestDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NewsDigestServiceImpl implements NewsDigestService {
    private NewsDigestDynamoRepository newsDigestDynamoRepository;

    @Autowired
    public NewsDigestServiceImpl(NewsDigestDynamoRepository newsDigestDynamoRepository) {
        this.newsDigestDynamoRepository = newsDigestDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create digest service - Digest Code :%s", createCommand.getCode()));

        Digest digest = Digest.builder()
                .code(createCommand.getCode())
                .title(createCommand.getTitle())
                .summary(createCommand.getSummary())
                .description(createCommand.getDescription())
                .imageUrl(createCommand.getImageUrl())
                .categories(createCommand.getCategories())
                .status("ACTIVE")
                .build();

        digest.setAddedOn(createCommand.getExecOn());
        digest.setUpdatedOn(createCommand.getExecOn());
        digest.setAddedBy(createCommand.getExecBy().getUserId());

        newsDigestDynamoRepository.save(digest);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update digest service - Digest Code:%s", updateCommand.getCode()));

        Digest digest = newsDigestDynamoRepository.findBy(updateCommand.getCode());
        if (digest != null) {
            digest.setTitle(updateCommand.getTitle());
            digest.setStatus("ACTIVE");
            digest.setSummary(updateCommand.getSummary());
            digest.setCategories(updateCommand.getCategories());
            digest.setImageUrl(updateCommand.getImageUrl());
            digest.setDescription(updateCommand.getDescription());
            digest.setUpdatedOn(updateCommand.getExecOn());
            digest.setUpdatedBy(updateCommand.getExecBy().getUserId());
            newsDigestDynamoRepository.save(digest);
        }
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable digest service - Digest Code:%s", disableCommand.getCode()));

        newsDigestDynamoRepository.findBy(disableCommand.getCode());
        Digest digest = newsDigestDynamoRepository.findBy(disableCommand.getCode());
        if (digest != null) {
            digest.setStatus("INACTIVE");
            digest.setUpdatedOn(disableCommand.getExecOn());
            digest.setUpdatedBy(disableCommand.getExecBy().getUserId());
            newsDigestDynamoRepository.save(digest);
        }
    }

    @Override
    public List<Digest> findAll() {

        log.info("Entering findAll Digest service");

        return newsDigestDynamoRepository.findAll();
    }

    @Override
    public Digest findBy(String code) {

        log.info(String.format("Entering findBy Digest service - Digest code:%s", code));

        return newsDigestDynamoRepository.findBy(code);
    }


}
