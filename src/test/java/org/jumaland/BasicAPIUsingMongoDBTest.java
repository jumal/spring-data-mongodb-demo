package org.jumaland;

import com.mongodb.MongoClient;

public class BasicAPIUsingMongoDBTest extends AbstractBasicAPITest {

    protected MongoClient client() throws Exception {
        return new MongoClient();
    }
}
