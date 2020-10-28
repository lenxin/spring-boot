package smoketest.actuator;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "lega.cy")
public class SampleLegacyEndpointWithDot {

	@ReadOperation
	public Map<String, String> example() {
		return Collections.singletonMap("legacy", "legacy");
	}

}
