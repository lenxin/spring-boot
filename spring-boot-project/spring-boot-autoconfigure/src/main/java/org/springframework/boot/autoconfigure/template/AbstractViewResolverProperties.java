package org.springframework.boot.autoconfigure.template;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.ViewResolver;

/**
 * Base class for {@link ConfigurationProperties @ConfigurationProperties} of a
 * {@link ViewResolver}.
 *


 * @since 1.2.0
 * @see AbstractTemplateViewResolverProperties
 */
public abstract class AbstractViewResolverProperties {

	private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	/**
	 * Whether to enable MVC view resolution for this technology.
	 */
	private boolean enabled = true;

	/**
	 * Whether to enable template caching.
	 */
	private boolean cache;

	/**
	 * Content-Type value.
	 */
	private MimeType contentType = DEFAULT_CONTENT_TYPE;

	/**
	 * Template encoding.
	 */
	private Charset charset = DEFAULT_CHARSET;

	/**
	 * View names that can be resolved.
	 */
	private String[] viewNames;

	/**
	 * Whether to check that the templates location exists.
	 */
	private boolean checkTemplateLocation = true;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setCheckTemplateLocation(boolean checkTemplateLocation) {
		this.checkTemplateLocation = checkTemplateLocation;
	}

	public boolean isCheckTemplateLocation() {
		return this.checkTemplateLocation;
	}

	public String[] getViewNames() {
		return this.viewNames;
	}

	public void setViewNames(String[] viewNames) {
		this.viewNames = viewNames;
	}

	public boolean isCache() {
		return this.cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public MimeType getContentType() {
		if (this.contentType.getCharset() == null) {
			Map<String, String> parameters = new LinkedHashMap<>();
			parameters.put("charset", this.charset.name());
			parameters.putAll(this.contentType.getParameters());
			return new MimeType(this.contentType, parameters);
		}
		return this.contentType;
	}

	public void setContentType(MimeType contentType) {
		this.contentType = contentType;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public String getCharsetName() {
		return (this.charset != null) ? this.charset.name() : null;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}
