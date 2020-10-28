package org.springframework.boot.autoconfigure.web.servlet;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.util.StringUtils;

/**
 * Default implementation of {@link JerseyApplicationPath} that derives the path from
 * {@link JerseyProperties} or the {@code @ApplicationPath} annotation.
 *

 * @since 2.1.0
 */
public class DefaultJerseyApplicationPath implements JerseyApplicationPath {

	private final String applicationPath;

	private final ResourceConfig config;

	public DefaultJerseyApplicationPath(String applicationPath, ResourceConfig config) {
		this.applicationPath = applicationPath;
		this.config = config;
	}

	@Override
	public String getPath() {
		return resolveApplicationPath();
	}

	private String resolveApplicationPath() {
		if (StringUtils.hasLength(this.applicationPath)) {
			return this.applicationPath;
		}
		// Jersey doesn't like to be the default servlet, so map to /* as a fallback
		return MergedAnnotations.from(this.config.getApplication().getClass(), SearchStrategy.TYPE_HIERARCHY)
				.get(ApplicationPath.class).getValue(MergedAnnotation.VALUE, String.class).orElse("/*");
	}

}
