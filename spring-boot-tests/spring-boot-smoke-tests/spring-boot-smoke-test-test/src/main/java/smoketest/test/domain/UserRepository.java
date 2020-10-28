package smoketest.test.domain;

import org.springframework.data.repository.Repository;

/**
 * Domain repository for {@link User}.
 *

 */
public interface UserRepository extends Repository<User, Long> {

	User findByUsername(String username);

}
