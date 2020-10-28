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
 * Tests for {@link DockerRegistryTokenAuthentication}.
 *

 */
class DockerRegistryTokenAuthenticationTests extends AbstractJsonTests {

	@Test
	void createAuthHeaderReturnsEncodedHeader() throws IOException, JSONException {
		DockerRegistryTokenAuthentication auth = new DockerRegistryTokenAuthentication("tokenvalue");
		String header = auth.getAuthHeader();
		String expectedJson = StreamUtils.copyToString(getContent("auth-token.json"), StandardCharsets.UTF_8);
		JSONAssert.assertEquals(expectedJson, new String(Base64Utils.decodeFromUrlSafeString(header)), false);
	}

}
