package com.tekcapzule.newsdigest.domain.service;

import com.tekcapzule.newsdigest.domain.command.*;
import com.tekcapzule.newsdigest.domain.model.Digest;

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
