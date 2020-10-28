package smoketest.actuator.customsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

/**
 * Integration tests for actuator endpoints with custom dispatcher servlet path.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "spring.mvc.servlet.path=/example")
class CustomServletPathSampleActuatorTests extends AbstractSampleActuatorCustomSecurityTests {

	@LocalServerPort
	private int port;

	@Autowired
	private Environment environment;

	@Override
	String getPath() {
		return "http://localhost:" + this.port + "/example";
	}

	@Override
	String getManagementPath() {
		return "http://localhost:" + this.port + "/example";
	}

	@Override
	Environment getEnvironment() {
		return this.environment;
	}

}
