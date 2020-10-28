package org.springframework.boot.buildpack.platform.docker.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.buildpack.platform.json.AbstractJsonTests;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;

/**
 * Tests for {@link DockerRegistryUserAuthentication}.
 *

 */
class DockerRegistryUserAuthenticationTests extends AbstractJsonTests {

	@Test
	void createMinimalAuthHeaderReturnsEncodedHeader() throws IOException, JSONException {
		DockerRegistryUserAuthentication auth = new DockerRegistryUserAuthentication("user", "secret",
				"https://docker.example.com", "docker@example.com");
		JSONAssert.assertEquals(jsonContent("auth-user-full.json"), decoded(auth.getAuthHeader()), false);
	}

	@Test
	void createFullAuthHeaderReturnsEncodedHeader() throws IOException, JSONException {
		DockerRegistryUserAuthentication auth = new DockerRegistryUserAuthentication("user", "secret", null, null);
		JSONAssert.assertEquals(jsonContent("auth-user-minimal.json"), decoded(auth.getAuthHeader()), false);
	}

	private String jsonContent(String s) throws IOException {
		return StreamUtils.copyToString(getContent(s), StandardCharsets.UTF_8);
	}

	private String decoded(String header) {
		return new String(Base64Utils.decodeFromUrlSafeString(header));
	}

}
