package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

/**
 * Example POJO used with {@link WebFluxTest @WebFluxTest} tests.
 *

 */
public class ExamplePojo {

	private final String alpha;

	private final String bravo;

	public ExamplePojo(String alpha, String bravo) {
		this.alpha = alpha;
		this.bravo = bravo;
	}

	public String getAlpha() {
		return this.alpha;
	}

	public String getBravo() {
		return this.bravo;
	}

}
