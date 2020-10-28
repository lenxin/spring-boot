package org.springframework.boot.web.server;

import org.apache.coyote.http11.Http11NioProtocol;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Compression}.
 *

 */
class CompressionTests {

	@Test
	void defaultCompressibleMimeTypesMatchesTomcatsDefault() {
		assertThat(new Compression().getMimeTypes()).containsExactlyInAnyOrder(getTomcatDefaultCompressibleMimeTypes());
	}

	private String[] getTomcatDefaultCompressibleMimeTypes() {
		Http11NioProtocol protocol = new Http11NioProtocol();
		return protocol.getCompressibleMimeTypes();
	}

}
