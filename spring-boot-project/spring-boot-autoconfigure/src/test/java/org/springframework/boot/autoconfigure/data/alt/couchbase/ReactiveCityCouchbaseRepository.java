package org.springframework.boot.autoconfigure.data.alt.couchbase;

import org.springframework.boot.autoconfigure.data.couchbase.city.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveCityCouchbaseRepository extends ReactiveCrudRepository<City, Long> {

}
