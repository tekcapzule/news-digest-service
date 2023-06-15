package com.tekcapsule.newsdigest.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.newsdigest.domain.model.Digest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class NewsDigestRepositoryImpl implements NewsDigestDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public NewsDigestRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Digest> findAll() {

        return dynamo.scan(Digest.class,new DynamoDBScanExpression());
    }

    @Override
    public Digest findBy(String code) {
        return dynamo.load(Digest.class, code);
    }

    @Override
    public Digest save(Digest digest) {
        dynamo.save(digest);
        return digest;
    }
}
