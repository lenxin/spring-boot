package org.springframework.boot.autoconfigure.data.solr.city;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface CityRepository extends Repository<City, String> {

	Page<City> findByNameStartingWith(String name, Pageable page);

}
