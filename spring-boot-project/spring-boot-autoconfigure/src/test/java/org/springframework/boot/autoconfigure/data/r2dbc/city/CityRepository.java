package org.springframework.boot.autoconfigure.data.r2dbc.city;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CityRepository extends ReactiveCrudRepository<City, Long> {

}
