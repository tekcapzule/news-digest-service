package com.tekcapsule.newsdigest.domain.service;

import com.tekcapsule.newsdigest.domain.command.*;
import com.tekcapsule.newsdigest.domain.model.Digest;

import java.util.List;


public interface NewsDigestService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    void approve(ApproveCommand approveCommand);

    List<Digest> findAll();

    Digest findBy(String code);
    void recommend(RecommendCommand recommendCommand);
}
