package com.tekcapsule.newsdigest.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.newsdigest.domain.model.Digest;

public interface NewsDigestDynamoRepository extends CrudRepository<Digest, String> {
}
