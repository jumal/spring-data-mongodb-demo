package org.jumaland;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class AbstractBasicAPITest {

    private MongoDatabase database;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasicAPITest.class);

    protected abstract MongoClient client() throws Exception;

    @Test
    public void insert() throws Exception {
        ObjectId id = insertTestDocument();
        assertThat(id, is(notNullValue()));
        database().getCollection("restaurant").deleteOne(eq("_id", id));
    }

    @Test
    public void update() throws Exception {
        ObjectId id = insertTestDocument();
        Document document = database().getCollection("restaurant").find(eq("_id", id)).first();
        assertThat(document.getString("cuisine"), is("Italian"));
        assertThat(document.get("lastModified"), is(nullValue()));

        UpdateResult result = database().getCollection("restaurant").updateOne(eq("_id", id),
                new Document("$set", new Document("cuisine", "American (New)"))
                        .append("$currentDate", new Document("lastModified", true)));

        assertThat(result.getModifiedCount(), is(1L));
        document = database().getCollection("restaurant").find(eq("_id", id)).first();
        assertThat(document.getString("cuisine"), is("American (New)"));
        assertThat(document.get("lastModified"), is(notNullValue()));

        database().getCollection("restaurant").deleteOne(eq("_id", id));
    }

    @Test
    public void aggregate() throws Exception {
        ObjectId id = insertTestDocument();
        AggregateIterable<Document> iterable = database().getCollection("restaurant").aggregate(asList(
                new Document("$match", new Document("borough", "Manhattan").append("cuisine", "Italian")),
                new Document("$group", new Document("_id", "$address.zipcode").append("count", new Document("$sum", 1)))));
        iterable.forEach((Block<Document>) document -> LOGGER.info(document.toJson()));
        database().getCollection("restaurant").deleteOne(eq("_id", id));
    }

    private ObjectId insertTestDocument() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        Document document = new Document("address", new Document().append("street", "2 Avenue")
                                                                  .append("zipcode", "10075")
                                                                  .append("building", "1480")
                                                                  .append("coord", asList(-73.9557413, 40.7720266)))
                                .append("borough", "Manhattan")
                                .append("cuisine", "Italian")
                                .append("grades", asList(
                                                    new Document()
                                                        .append("date", format.parse("2014-10-01T00:00:00Z"))
                                                        .append("grade", "A")
                                                        .append("score", 11),
                                                    new Document()
                                                        .append("date", format.parse("2014-01-16T00:00:00Z"))
                                                        .append("grade", "B")
                                                        .append("score", 17)))
                                .append("name", "Vella")
                                .append("restaurant_id", "41704620");

        database().getCollection("restaurant").insertOne(document);
        return document.getObjectId("_id");
    }

    private MongoDatabase database() throws Exception {
        if (database == null) {
            database = client().getDatabase("test");
        }
        return database;
    }
}
