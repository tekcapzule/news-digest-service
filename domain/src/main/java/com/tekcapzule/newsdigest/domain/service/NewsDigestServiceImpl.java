package com.tekcapzule.newsdigest.domain.service;

import com.tekcapzule.newsdigest.domain.command.*;
import com.tekcapzule.newsdigest.domain.model.Digest;
import com.tekcapzule.newsdigest.domain.model.Status;
import com.tekcapzule.newsdigest.domain.repository.NewsDigestDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

        log.info(String.format("Entering create digest service - Digest :%s", createCommand.getTitle()));

        String code= UUID.randomUUID().toString();
        Digest digest = Digest.builder()
                .code(code)
                .title(createCommand.getTitle())
                .description(createCommand.getDescription())
                .author(createCommand.getAuthor())
                .schedule(createCommand.getSchedule())
                .imageUrl(createCommand.getImageUrl())
                .resourceUrl(createCommand.getResourceUrl())
                .category(createCommand.getCategory())
                .summary(createCommand.getSummary())
                .status(Status.SUBMITTED)
                .recommendations(createCommand.getRecommendations())
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
            digest.setSummary(updateCommand.getSummary());
            digest.setDescription(updateCommand.getDescription());
            digest.setCategory(updateCommand.getCategory());
            digest.setImageUrl(updateCommand.getImageUrl());
            digest.setResourceUrl(updateCommand.getResourceUrl());
            digest.setAuthor(updateCommand.getAuthor());
            digest.setSchedule(updateCommand.getSchedule());
            digest.setUpdatedOn(updateCommand.getExecOn());
            digest.setUpdatedBy(updateCommand.getExecBy().getUserId());
            digest.setRecommendations(updateCommand.getRecommendations());
            newsDigestDynamoRepository.save(digest);
        }
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable digest service - Digest Code:%s", disableCommand.getCode()));

        newsDigestDynamoRepository.findBy(disableCommand.getCode());
        Digest digest = newsDigestDynamoRepository.findBy(disableCommand.getCode());
        if (digest != null) {
            digest.setStatus(Status.INACTIVE);
            digest.setUpdatedOn(disableCommand.getExecOn());
            digest.setUpdatedBy(disableCommand.getExecBy().getUserId());
            newsDigestDynamoRepository.save(digest);
        }
    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve digest service - Digest Code:%s", approveCommand.getCode()));

        newsDigestDynamoRepository.findBy(approveCommand.getCode());
        Digest digest = newsDigestDynamoRepository.findBy(approveCommand.getCode());
        if (digest != null) {
            digest.setStatus(Status.ACTIVE);
            digest.setUpdatedOn(approveCommand.getExecOn());
            digest.setUpdatedBy(approveCommand.getExecBy().getUserId());
            newsDigestDynamoRepository.save(digest);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend newsdigest service -  newsdigest code:%s", recommendCommand.getCode()));

        Digest digest = newsDigestDynamoRepository.findBy(recommendCommand.getCode());
        if (digest != null) {
            Integer recommendationsCount = digest.getRecommendations();
            recommendationsCount += 1;
            digest.setRecommendations(recommendationsCount);

            digest.setUpdatedOn(recommendCommand.getExecOn());
            digest.setUpdatedBy(recommendCommand.getExecBy().getUserId());

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
