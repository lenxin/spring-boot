package org.springframework.boot.test.autoconfigure.orm.jpa;

import org.springframework.data.repository.Repository;

/**
 * Example repository used with {@link DataJpaTest @DataJpaTest} tests.
 *

 */
interface ExampleRepository extends Repository<ExampleEntity, Long> {

	ExampleEntity findByReference(String reference);

}
