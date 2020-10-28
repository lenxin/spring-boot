package org.springframework.boot.web.reactive.result.view;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;

import com.samskivert.mustache.Mustache;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MustacheView}.
 *

 */
class MustacheViewTests {

	private final String templateUrl = "classpath:/" + getClass().getPackage().getName().replace(".", "/")
			+ "/template.html";

	private final StaticApplicationContext context = new StaticApplicationContext();

	@Test
	void viewResolvesHandlebars() {
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test").build());
		MustacheView view = new MustacheView();
		view.setCompiler(Mustache.compiler());
		view.setUrl(this.templateUrl);
		view.setCharset(StandardCharsets.UTF_8.displayName());
		view.setApplicationContext(this.context);
		view.render(Collections.singletonMap("World", "Spring"), MediaType.TEXT_HTML, exchange)
				.block(Duration.ofSeconds(30));
		StepVerifier.create(exchange.getResponse().getBodyAsString())
				.assertNext((body) -> assertThat(body).isEqualToIgnoringWhitespace("Hello Spring")).verifyComplete();
	}

}
