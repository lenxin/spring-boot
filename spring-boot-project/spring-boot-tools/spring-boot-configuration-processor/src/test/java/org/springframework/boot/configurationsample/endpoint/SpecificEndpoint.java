package org.springframework.boot.configurationsample.endpoint;

import org.springframework.boot.configurationsample.MetaEndpoint;
import org.springframework.boot.configurationsample.ReadOperation;
import org.springframework.lang.Nullable;

/**
 * A meta-annotated endpoint similar to {@code @WebEndpoint} or {@code @JmxEndpoint} in
 * Spring Boot. Also with a package private read operation that has an optional argument.
 *

 */
@MetaEndpoint(id = "specific", enableByDefault = true)
public class SpecificEndpoint {

	@ReadOperation
	String invoke(@Nullable String param) {
		return "test";
	}

}
