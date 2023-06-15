package com.tekcapsule.newsdigest.application.function;

import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.newsdigest.application.config.AppConfig;
import com.tekcapsule.newsdigest.domain.model.Digest;
import com.tekcapsule.newsdigest.domain.service.NewsDigestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllFunction implements Function<Message<EmptyFunctionInput>, Message<List<Digest>>> {

    private final NewsDigestService newsDigestService;

    private final AppConfig appConfig;

    public GetAllFunction(final NewsDigestService newsDigestService, final AppConfig appConfig) {
        this.newsDigestService = newsDigestService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Digest>> apply(Message<EmptyFunctionInput> getAllInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Digest> digests = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all digests Function");
            digests = newsDigestService.findAll();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(digests, responseHeaders);
    }
}