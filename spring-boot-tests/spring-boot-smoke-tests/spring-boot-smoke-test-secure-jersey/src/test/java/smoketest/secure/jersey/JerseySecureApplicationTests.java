package smoketest.secure.jersey;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Integration tests for actuator endpoints with custom security configuration.
 *


 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JerseySecureApplicationTests extends AbstractJerseySecureTests {

	@LocalServerPort
	private int port;

	@Override
	String getPath() {
		return "http://localhost:" + this.port;
	}

	@Override
	String getManagementPath() {
		return "http://localhost:" + this.port;
	}

}
