package org.springframework.boot.docs.builder;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Examples of using {@link SpringApplicationBuilder}.
 *

 */
public class SpringApplicationBuilderExample {

	public void hierarchyWithDisabledBanner(String[] args) {
		// @formatter:off
		// tag::hierarchy[]
		new SpringApplicationBuilder()
				.sources(Parent.class)
				.child(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
		// end::hierarchy[]
		// @formatter:on
	}

	/**
	 * Parent application configuration.
	 */
	static class Parent {

	}

	/**
	 * Application configuration.
	 */
	static class Application {

	}

}
