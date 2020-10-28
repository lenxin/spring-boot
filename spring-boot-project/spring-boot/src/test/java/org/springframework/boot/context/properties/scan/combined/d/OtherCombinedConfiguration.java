package org.springframework.boot.context.properties.scan.combined.d;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

/**

 */
public class OtherCombinedConfiguration {

	@RestController
	@ConfigurationProperties(prefix = "c")
	static class MyControllerProperties {

	}

}
