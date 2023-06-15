package com.tekcapsule.newsdigest.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.newsdigest.application.config.AppConfig;
import com.tekcapsule.newsdigest.application.function.input.GetInput;
import com.tekcapsule.newsdigest.domain.model.Digest;
import com.tekcapsule.newsdigest.domain.service.NewsDigestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetFunction implements Function<Message<GetInput>, Message<Digest>> {

    private final NewsDigestService newsDigestService;

    private final AppConfig appConfig;

    public GetFunction(final NewsDigestService newsDigestService, final AppConfig appConfig) {
        this.newsDigestService = newsDigestService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<Digest> apply(Message<GetInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Digest digest = new Digest();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get digest Function -Digest Code:%s", getInput.getCode()));
            digest = newsDigestService.findBy(getInput.getCode());
            if (digest == null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
                digest = Digest.builder().build();
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(digest, responseHeaders);
    }
}