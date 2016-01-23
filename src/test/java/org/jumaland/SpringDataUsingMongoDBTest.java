package org.jumaland;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SpringDataUsingMongoDBTest {

	@Autowired
    private RestaurantRepository repository;

	@Test
	public void insert() {
		Restaurant restaurant = repository.insert(newInstance());
		assertNotNull(restaurant.getId());
		repository.delete(restaurant.getId());
	}

    @Test
    public void update() {
        Restaurant restaurant = repository.insert(newInstance());
        restaurant.setCuisine("American (New)");
        repository.save(restaurant);

        restaurant = repository.findOne(restaurant.getId());
        assertEquals("American (New)", restaurant.getCuisine());

        repository.delete(restaurant.getId());
    }

    @Test
    public void aggregate() {
        long initial = repository.findByCuisine("Italian").count();

        Restaurant restaurant = repository.insert(newInstance());

        assertTrue(repository.findByCuisine("Italian").count() == initial + 1);

        repository.delete(restaurant.getId());
    }

    private Restaurant newInstance() {
        Address address = new Address("2 Avenue", "10075", "1480", asList(-73.9557413, 40.7720266));
        List<Grade> grades = asList(new Grade("A", 11, new Date()), new Grade("B", 17, new Date()));
        return new Restaurant("Vella", address, "Manhattan", "Italian", grades);
    }
}
