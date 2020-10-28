package org.springframework.boot.autoconfigure.data.alt.redis;

import org.springframework.boot.autoconfigure.data.redis.city.City;
import org.springframework.data.repository.Repository;

public interface CityRedisRepository extends Repository<City, Long> {

}
