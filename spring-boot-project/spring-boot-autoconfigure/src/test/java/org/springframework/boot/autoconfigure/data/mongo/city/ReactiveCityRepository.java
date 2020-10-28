package org.springframework.boot.autoconfigure.data.mongo.city;

import reactor.core.publisher.Flux;

import org.springframework.data.repository.Repository;

public interface ReactiveCityRepository extends Repository<City, Long> {

	Flux<City> findAll();

}
