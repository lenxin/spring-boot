package org.springframework.boot.web.servlet.server;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 * Configuration properties for server HTTP encoding.
 *



 * @since 2.3.0
 */
public class Encoding {

	/**
	 * Default HTTP encoding for Servlet applications.
	 */
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	/**
	 * Charset of HTTP requests and responses. Added to the "Content-Type" header if not
	 * set explicitly.
	 */
	private Charset charset = DEFAULT_CHARSET;

	/**
	 * Whether to force the encoding to the configured charset on HTTP requests and
	 * responses.
	 */
	private Boolean force;

	/**
	 * Whether to force the encoding to the configured charset on HTTP requests. Defaults
	 * to true when "force" has not been specified.
	 */
	private Boolean forceRequest;

	/**
	 * Whether to force the encoding to the configured charset on HTTP responses.
	 */
	private Boolean forceResponse;

	/**
	 * Locale in which to encode mapping.
	 */
	private Map<Locale, Charset> mapping;

	public Charset getCharset() {
		return this.charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public boolean isForce() {
		return Boolean.TRUE.equals(this.force);
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public boolean isForceRequest() {
		return Boolean.TRUE.equals(this.forceRequest);
	}

	public void setForceRequest(boolean forceRequest) {
		this.forceRequest = forceRequest;
	}

	public boolean isForceResponse() {
		return Boolean.TRUE.equals(this.forceResponse);
	}

	public void setForceResponse(boolean forceResponse) {
		this.forceResponse = forceResponse;
	}

	public Map<Locale, Charset> getMapping() {
		return this.mapping;
	}

	public void setMapping(Map<Locale, Charset> mapping) {
		this.mapping = mapping;
	}

	public boolean shouldForce(Type type) {
		Boolean force = (type != Type.REQUEST) ? this.forceResponse : this.forceRequest;
		if (force == null) {
			force = this.force;
		}
		if (force == null) {
			force = (type == Type.REQUEST);
		}
		return force;
	}

	/**
	 * Type of HTTP message to consider for encoding configuration.
	 */
	public enum Type {

		/**
		 * HTTP request message.
		 */
		REQUEST,
		/**
		 * HTTP response message.
		 */
		RESPONSE

	}

}
