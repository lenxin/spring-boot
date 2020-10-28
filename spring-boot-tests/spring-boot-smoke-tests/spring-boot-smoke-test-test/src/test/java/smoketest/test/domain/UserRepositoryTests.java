package smoketest.test.domain;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UserRepository}.
 *

 */
@DataJpaTest
class UserRepositoryTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	void findByUsernameShouldReturnUser() {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("sboot");
		assertThat(user.getUsername()).isEqualTo("sboot");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

	@Test
	void findByUsernameWhenNoUserShouldReturnNull() {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("mmouse");
		assertThat(user).isNull();
	}

}
