package org.springframework.boot.autoconfigure.data.alt.mongo;

import org.springframework.boot.autoconfigure.data.mongo.city.City;
import org.springframework.data.repository.Repository;

public interface CityMongoDbRepository extends Repository<City, Long> {

}
