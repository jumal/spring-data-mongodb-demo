package org.jumaland;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

public class BasicAPIUsingFongoTest extends AbstractBasicAPITest {

    protected MongoClient client() throws Exception {
        return new Fongo(null).getMongo();
    }
}
