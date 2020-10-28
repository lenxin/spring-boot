package smoketest.data.jpa.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import smoketest.data.jpa.domain.City;
import smoketest.data.jpa.domain.Hotel;
import smoketest.data.jpa.domain.HotelSummary;
import smoketest.data.jpa.domain.Rating;
import smoketest.data.jpa.domain.RatingCount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link HotelRepository}.
 *

 */
@SpringBootTest
class HotelRepositoryIntegrationTests {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	HotelRepository repository;

	@Test
	void executesQueryMethodsCorrectly() {
		City city = this.cityRepository.findAll(PageRequest.of(0, 1, Direction.ASC, "name")).getContent().get(0);
		assertThat(city.getName()).isEqualTo("Atlanta");
		Page<HotelSummary> hotels = this.repository.findByCity(city, PageRequest.of(0, 10, Direction.ASC, "name"));
		Hotel hotel = this.repository.findByCityAndName(city, hotels.getContent().get(0).getName());
		assertThat(hotel.getName()).isEqualTo("Doubletree");
		List<RatingCount> counts = this.repository.findRatingCounts(hotel);
		assertThat(counts).hasSize(1);
		assertThat(counts.get(0).getRating()).isEqualTo(Rating.AVERAGE);
		assertThat(counts.get(0).getCount()).isGreaterThan(1L);
	}

}
