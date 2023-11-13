package com.tekcapzule.newsdigest.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.newsdigest.domain.model.Digest;

public interface NewsDigestDynamoRepository extends CrudRepository<Digest, String> {
}
