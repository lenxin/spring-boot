package smoketest.data.neo4j;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

	Customer findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}
