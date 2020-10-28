package org.springframework.boot.autoconfigure.data.couchbase.city;

import reactor.core.publisher.Mono;

import org.springframework.data.repository.Repository;

public interface ReactiveCityRepository extends Repository<City, Long> {

	Mono<City> save(City city);

	Mono<City> findById(Long id);

}
