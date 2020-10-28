package smoketest.test.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smoketest.test.domain.User;
import smoketest.test.domain.UserRepository;
import smoketest.test.domain.VehicleIdentificationNumber;
import smoketest.test.service.VehicleDetails;
import smoketest.test.service.VehicleDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link UserVehicleService}.
 *

 */
@ExtendWith(MockitoExtension.class)
class UserVehicleServiceTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Mock
	private VehicleDetailsService vehicleDetailsService;

	@Mock
	private UserRepository userRepository;

	private UserVehicleService service;

	@BeforeEach
	void setup() {
		this.service = new UserVehicleService(this.userRepository, this.vehicleDetailsService);
	}

	@Test
	void getVehicleDetailsWhenUsernameIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.service.getVehicleDetails(null))
				.withMessage("Username must not be null");
	}

	@Test
	void getVehicleDetailsWhenUsernameNotFoundShouldThrowException() {
		given(this.userRepository.findByUsername(anyString())).willReturn(null);
		assertThatExceptionOfType(UserNameNotFoundException.class)
				.isThrownBy(() -> this.service.getVehicleDetails("sboot"));
	}

	@Test
	void getVehicleDetailsShouldReturnMakeAndModel() {
		given(this.userRepository.findByUsername(anyString())).willReturn(new User("sboot", VIN));
		VehicleDetails details = new VehicleDetails("Honda", "Civic");
		given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(details);
		VehicleDetails actual = this.service.getVehicleDetails("sboot");
		assertThat(actual).isEqualTo(details);
	}

}
