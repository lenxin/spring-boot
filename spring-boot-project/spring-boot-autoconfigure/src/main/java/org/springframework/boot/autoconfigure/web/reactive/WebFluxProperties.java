package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.util.StringUtils;

/**
 * {@link ConfigurationProperties properties} for Spring WebFlux.
 *

 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "spring.webflux")
public class WebFluxProperties {

	/**
	 * Base path for all web handlers.
	 */
	private String basePath;

	private final Format format = new Format();

	/**
	 * Path pattern used for static resources.
	 */
	private String staticPathPattern = "/**";

	public String getBasePath() {
		return this.basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = cleanBasePath(basePath);
	}

	private String cleanBasePath(String basePath) {
		String candidate = StringUtils.trimWhitespace(basePath);
		if (StringUtils.hasText(candidate)) {
			if (!candidate.startsWith("/")) {
				candidate = "/" + candidate;
			}
			if (candidate.endsWith("/")) {
				candidate = candidate.substring(0, candidate.length() - 1);
			}
		}
		return candidate;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(replacement = "spring.webflux.format.date")
	public String getDateFormat() {
		return this.format.getDate();
	}

	@Deprecated
	public void setDateFormat(String dateFormat) {
		this.format.setDate(dateFormat);
	}

	public Format getFormat() {
		return this.format;
	}

	public String getStaticPathPattern() {
		return this.staticPathPattern;
	}

	public void setStaticPathPattern(String staticPathPattern) {
		this.staticPathPattern = staticPathPattern;
	}

	public static class Format {

		/**
		 * Date format to use, for example `dd/MM/yyyy`.
		 */
		private String date;

		/**
		 * Time format to use, for example `HH:mm:ss`.
		 */
		private String time;

		/**
		 * Date-time format to use, for example `yyyy-MM-dd HH:mm:ss`.
		 */
		private String dateTime;

		public String getDate() {
			return this.date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return this.time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getDateTime() {
			return this.dateTime;
		}

		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}

	}

}
