package org.jumaland;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.lordofthejars.nosqlunit.core.LoadStrategyEnum.CLEAN_INSERT;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class SpringDataUsingNoSQLUnitWithInMemoryServerTest {

    @Rule
    public MongoDbRule rule = newMongoDbRule().defaultSpringMongoDb("test");

    @Autowired
    public ApplicationContext applicationContext;

    @Autowired
    private RestaurantRepository repository;

    @Test
    @UsingDataSet(locations = "initial-data.json", loadStrategy = CLEAN_INSERT)
    @ShouldMatchDataSet(location = "expected-data.json")
    public void update() {
        Restaurant restaurant = repository.findByCuisine("Italian").findFirst().get();
        restaurant.setCuisine("French");
        repository.save(restaurant);
    }

    @Configuration
    static class MongoDbTestConfiguration extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "test";
        }

        @Bean
        @Override
        public Mongo mongo() {
            return new Fongo(null).getMongo();
        }
    }
}
