package smoketest.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smoketest.test.domain.VehicleIdentificationNumber;
import smoketest.test.service.VehicleDetails;
import smoketest.test.service.VehicleDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * {@code @SpringBootTest} with a random port for {@link SampleTestApplication}.
 *


 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class SampleTestApplicationWebIntegrationTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("01234567890123456");

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private VehicleDetailsService vehicleDetailsService;

	@BeforeEach
	void setup() {
		given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(new VehicleDetails("Honda", "Civic"));
	}

	@Test
	void test() {
		assertThat(this.restTemplate.getForEntity("/{username}/vehicle", String.class, "sframework").getStatusCode())
				.isEqualTo(HttpStatus.OK);
	}

}
