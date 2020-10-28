package org.springframework.boot.autoconfigure.data.alt.solr;

import org.springframework.boot.autoconfigure.data.solr.city.City;
import org.springframework.data.repository.Repository;

public interface CitySolrRepository extends Repository<City, String> {

}
