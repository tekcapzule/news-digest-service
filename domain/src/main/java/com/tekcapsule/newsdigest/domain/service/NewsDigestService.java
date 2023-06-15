package com.tekcapsule.newsdigest.domain.service;

import com.tekcapsule.newsdigest.domain.command.CreateCommand;
import com.tekcapsule.newsdigest.domain.command.DisableCommand;
import com.tekcapsule.newsdigest.domain.command.UpdateCommand;
import com.tekcapsule.newsdigest.domain.model.Digest;

import java.util.List;


public interface NewsDigestService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Digest> findAll();

    Digest findBy(String code);
}
