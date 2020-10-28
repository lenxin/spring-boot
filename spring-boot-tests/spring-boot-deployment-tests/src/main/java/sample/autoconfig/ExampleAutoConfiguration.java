package sample.autoconfig;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnWarDeployment
@Configuration
public class ExampleAutoConfiguration {

	@Bean
	public TestEndpoint testEndpoint() {
		return new TestEndpoint();
	}

	@Endpoint(id = "war")
	static class TestEndpoint {

		@ReadOperation
		String hello() {
			return "{\"hello\":\"world\"}";
		}

	}

}
