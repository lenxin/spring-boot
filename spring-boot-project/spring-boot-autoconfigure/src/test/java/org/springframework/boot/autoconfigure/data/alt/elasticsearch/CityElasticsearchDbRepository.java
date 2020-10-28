package org.springframework.boot.autoconfigure.data.alt.elasticsearch;

import org.springframework.boot.autoconfigure.data.elasticsearch.city.City;
import org.springframework.data.repository.Repository;

public interface CityElasticsearchDbRepository extends Repository<City, Long> {

}
