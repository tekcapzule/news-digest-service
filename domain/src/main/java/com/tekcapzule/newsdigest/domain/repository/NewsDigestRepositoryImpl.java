package com.tekcapzule.newsdigest.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapzule.newsdigest.domain.model.Digest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class NewsDigestRepositoryImpl implements NewsDigestDynamoRepository {

    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public NewsDigestRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Digest> findAll() {
        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<Digest> queryExpression = new DynamoDBQueryExpression<Digest>()
                .withIndexName("categoryGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Digest.class, queryExpression);
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
