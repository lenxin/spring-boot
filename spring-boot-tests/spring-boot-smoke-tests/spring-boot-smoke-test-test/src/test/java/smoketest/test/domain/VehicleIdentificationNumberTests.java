package smoketest.test.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link VehicleIdentificationNumber}.
 *

 * @see <a href="https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests</a>
 * @see <a href="https://joel-costigliola.github.io/assertj/">AssertJ</a>
 */
class VehicleIdentificationNumberTests {

	private static final String SAMPLE_VIN = "41549485710496749";

	@Test
	void createWhenVinIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new VehicleIdentificationNumber(null))
				.withMessage("VIN must not be null");
	}

	@Test
	void createWhenVinIsMoreThan17CharsShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new VehicleIdentificationNumber("012345678901234567"))
				.withMessage("VIN must be exactly 17 characters");
	}

	@Test
	void createWhenVinIsLessThan17CharsShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new VehicleIdentificationNumber("0123456789012345"))
				.withMessage("VIN must be exactly 17 characters");
	}

	@Test
	void toStringShouldReturnVin() {
		VehicleIdentificationNumber vin = new VehicleIdentificationNumber(SAMPLE_VIN);
		assertThat(vin.toString()).isEqualTo(SAMPLE_VIN);
	}

	@Test
	void equalsAndHashCodeShouldBeBasedOnVin() {
		VehicleIdentificationNumber vin1 = new VehicleIdentificationNumber(SAMPLE_VIN);
		VehicleIdentificationNumber vin2 = new VehicleIdentificationNumber(SAMPLE_VIN);
		VehicleIdentificationNumber vin3 = new VehicleIdentificationNumber("00000000000000000");
		assertThat(vin1.hashCode()).isEqualTo(vin2.hashCode());
		assertThat(vin1).isEqualTo(vin1).isEqualTo(vin2).isNotEqualTo(vin3);
	}

}
