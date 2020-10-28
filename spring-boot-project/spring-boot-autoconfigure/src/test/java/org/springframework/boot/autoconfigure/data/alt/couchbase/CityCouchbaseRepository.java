package org.springframework.boot.autoconfigure.data.alt.couchbase;

import org.springframework.boot.autoconfigure.data.couchbase.city.City;
import org.springframework.data.repository.Repository;

public interface CityCouchbaseRepository extends Repository<City, Long> {

}
