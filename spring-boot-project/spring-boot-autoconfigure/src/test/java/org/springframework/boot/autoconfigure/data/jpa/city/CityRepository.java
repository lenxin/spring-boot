package org.springframework.boot.autoconfigure.data.jpa.city;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

	@Override
	Page<City> findAll(Pageable pageable);

	Page<City> findByNameLikeAndCountryLikeAllIgnoringCase(String name, String country, Pageable pageable);

	City findByNameAndCountryAllIgnoringCase(String name, String country);

}
