package smoketest.test.domain;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Data JPA tests for {@link User}.
 *

 */
@DataJpaTest
class UserEntityTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Autowired
	private TestEntityManager entityManager;

	@Test
	void createWhenUsernameIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new User(null, VIN))
				.withMessage("Username must not be empty");
	}

	@Test
	void createWhenUsernameIsEmptyShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new User("", VIN))
				.withMessage("Username must not be empty");
	}

	@Test
	void createWhenVinIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new User("sboot", null))
				.withMessage("VIN must not be null");
	}

	@Test
	void saveShouldPersistData() {
		User user = this.entityManager.persistFlushFind(new User("sboot", VIN));
		assertThat(user.getUsername()).isEqualTo("sboot");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

}
