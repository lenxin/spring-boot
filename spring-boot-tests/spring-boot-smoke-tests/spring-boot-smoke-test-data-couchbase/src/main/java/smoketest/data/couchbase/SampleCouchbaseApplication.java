package smoketest.data.couchbase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleCouchbaseApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SampleCouchbaseApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.deleteAll();
		User user = saveUser();
		System.out.println(this.userRepository.findById(user.getId()));
	}

	private User saveUser() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstName("Alice");
		user.setLastName("Smith");
		return this.userRepository.save(user);
	}

}
