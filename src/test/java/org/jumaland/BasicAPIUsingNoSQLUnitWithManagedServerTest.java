package org.jumaland;

import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

import static com.lordofthejars.nosqlunit.core.LoadStrategyEnum.CLEAN_INSERT;

import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

import javax.inject.Inject;

public class BasicAPIUsingNoSQLUnitWithManagedServerTest extends AbstractBasicAPITest {

    @ClassRule
    public static final ManagedMongoDb DATABASE = newManagedMongoDbRule().mongodPath("/usr/local").build();

    @Rule
    public MongoDbRule rule = newMongoDbRule().defaultManagedMongoDb("test");

    @Inject
    private MongoClient client;

    @Override
    protected MongoClient client() throws Exception {
        return client;
    }

    @Test
    @UsingDataSet(locations = "initial-data.json", loadStrategy = CLEAN_INSERT)
    @ShouldMatchDataSet(location = "expected-data.json")
    @Override
    public void update() {
        client.getDatabase("test").getCollection("restaurant")
                .updateOne(eq("cuisine", "Italian"), new Document("$set", new Document("cuisine", "French")));
    }
}
