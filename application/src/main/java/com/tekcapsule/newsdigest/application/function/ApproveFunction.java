package com.tekcapsule.newsdigest.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.newsdigest.application.config.AppConfig;
import com.tekcapsule.newsdigest.application.function.input.ApproveInput;
import com.tekcapsule.newsdigest.application.mapper.InputOutputMapper;
import com.tekcapsule.newsdigest.domain.command.ApproveCommand;
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
public class ApproveFunction implements Function<Message<ApproveInput>, Message<Void>> {

    private final NewsDigestService newsDigestService;

    private final AppConfig appConfig;

    public ApproveFunction(final NewsDigestService newsDigestService, final AppConfig appConfig) {
        this.newsDigestService = newsDigestService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<ApproveInput> approveInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            ApproveInput approveInput = approveInputMessage.getPayload();
            log.info(String.format("Entering digest approve function - Digest Code:%s", approveInput.getCode()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(approveInputMessage.getHeaders());
            ApproveCommand approveCommand = InputOutputMapper.buildApproveCommandFromApproveInput.apply(approveInput, origin);
            newsDigestService.approve(approveCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }

        return new GenericMessage(payload, responseHeaders);
    }
}
