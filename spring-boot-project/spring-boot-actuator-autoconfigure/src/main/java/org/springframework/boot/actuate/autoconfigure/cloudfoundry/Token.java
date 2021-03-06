package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.CloudFoundryAuthorizationException.Reason;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

/**
 * The JSON web token provided with each request that originates from Cloud Foundry.
 *

 * @since 1.5.22
 */
public class Token {

	private final String encoded;

	private final String signature;

	private final Map<String, Object> header;

	private final Map<String, Object> claims;

	public Token(String encoded) {
		this.encoded = encoded;
		int firstPeriod = encoded.indexOf('.');
		int lastPeriod = encoded.lastIndexOf('.');
		if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
			throw new CloudFoundryAuthorizationException(Reason.INVALID_TOKEN,
					"JWT must have header, body and signature");
		}
		this.header = parseJson(encoded.substring(0, firstPeriod));
		this.claims = parseJson(encoded.substring(firstPeriod + 1, lastPeriod));
		this.signature = encoded.substring(lastPeriod + 1);
		if (!StringUtils.hasLength(this.signature)) {
			throw new CloudFoundryAuthorizationException(Reason.INVALID_TOKEN,
					"Token must have non-empty crypto segment");
		}
	}

	private Map<String, Object> parseJson(String base64) {
		try {
			byte[] bytes = Base64Utils.decodeFromUrlSafeString(base64);
			return JsonParserFactory.getJsonParser().parseMap(new String(bytes, StandardCharsets.UTF_8));
		}
		catch (RuntimeException ex) {
			throw new CloudFoundryAuthorizationException(Reason.INVALID_TOKEN, "Token could not be parsed", ex);
		}
	}

	public byte[] getContent() {
		return this.encoded.substring(0, this.encoded.lastIndexOf('.')).getBytes();
	}

	public byte[] getSignature() {
		return Base64Utils.decodeFromUrlSafeString(this.signature);
	}

	public String getSignatureAlgorithm() {
		return getRequired(this.header, "alg", String.class);
	}

	public String getIssuer() {
		return getRequired(this.claims, "iss", String.class);
	}

	public long getExpiry() {
		return getRequired(this.claims, "exp", Integer.class).longValue();
	}

	@SuppressWarnings("unchecked")
	public List<String> getScope() {
		return getRequired(this.claims, "scope", List.class);
	}

	public String getKeyId() {
		return getRequired(this.header, "kid", String.class);
	}

	@SuppressWarnings("unchecked")
	private <T> T getRequired(Map<String, Object> map, String key, Class<T> type) {
		Object value = map.get(key);
		if (value == null) {
			throw new CloudFoundryAuthorizationException(Reason.INVALID_TOKEN, "Unable to get value from key " + key);
		}
		if (!type.isInstance(value)) {
			throw new CloudFoundryAuthorizationException(Reason.INVALID_TOKEN,
					"Unexpected value type from key " + key + " value " + value);
		}
		return (T) value;
	}

	@Override
	public String toString() {
		return this.encoded;
	}

}
