package org.jumaland;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Stream<Restaurant> findByCuisine(String cuisine);
}