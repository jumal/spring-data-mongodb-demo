package org.jumaland;

import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

import static com.lordofthejars.nosqlunit.core.LoadStrategyEnum.CLEAN_INSERT;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static com.mongodb.client.model.Filters.eq;

public class BasicAPIUsingNoSQLUnitWithInMemoryServerTest extends AbstractBasicAPITest {

    @ClassRule
    public static final InMemoryMongoDb DATABASE = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule rule = newMongoDbRule().defaultEmbeddedMongoDb("test");

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
