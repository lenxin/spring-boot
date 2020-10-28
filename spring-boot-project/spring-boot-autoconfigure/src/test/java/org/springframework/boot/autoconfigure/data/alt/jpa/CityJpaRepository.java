package org.springframework.boot.autoconfigure.data.alt.jpa;

import org.springframework.boot.autoconfigure.data.jpa.city.City;
import org.springframework.data.repository.Repository;

public interface CityJpaRepository extends Repository<City, Long> {

}
