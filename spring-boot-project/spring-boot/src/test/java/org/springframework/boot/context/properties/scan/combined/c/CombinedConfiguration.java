package org.springframework.boot.context.properties.scan.combined.c;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**

 */
public class CombinedConfiguration {

	@Component
	@ConfigurationProperties(prefix = "b")
	static class MyProperties {

	}

}
