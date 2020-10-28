package org.springframework.boot.autoconfigure.data.alt.elasticsearch;

import org.springframework.boot.autoconfigure.data.elasticsearch.city.City;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface CityReactiveElasticsearchDbRepository extends ReactiveElasticsearchRepository<City, Long> {

}
