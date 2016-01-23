package org.jumaland;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.junit.AfterClass;

public class BasicAPIUsingEmbeddedMongoDBTest extends AbstractBasicAPITest {

    private static MongodForTestsFactory factory;

    protected MongoClient client() throws Exception {
        factory = new MongodForTestsFactory();
        return factory.newMongo();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.shutdown();
    }
}
