package org.springframework.boot.autoconfigure.data.alt.cassandra;

import org.springframework.boot.autoconfigure.data.cassandra.city.City;
import org.springframework.data.repository.Repository;

public interface CityCassandraRepository extends Repository<City, Long> {

}
