package org.springframework.boot.autoconfigure.data.alt.mongo;

import org.springframework.boot.autoconfigure.data.mongo.city.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveCityMongoDbRepository extends ReactiveCrudRepository<City, Long> {

}
