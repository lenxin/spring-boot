package org.springframework.boot.actuate.endpoint.web.servlet;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerMapping;

/**
 * A custom {@link HandlerMapping} that makes web endpoints available over HTTP using
 * Spring MVC.
 *


 * @since 2.0.0
 */
public class WebMvcEndpointHandlerMapping extends AbstractWebMvcEndpointHandlerMapping {

	private final EndpointLinksResolver linksResolver;

	/**
	 * Creates a new {@code WebMvcEndpointHandlerMapping} instance that provides mappings
	 * for the given endpoints.
	 * @param endpointMapping the base mapping for all endpoints
	 * @param endpoints the web endpoints
	 * @param endpointMediaTypes media types consumed and produced by the endpoints
	 * @param corsConfiguration the CORS configuration for the endpoints or {@code null}
	 * @param linksResolver resolver for determining links to available endpoints
	 * @param shouldRegisterLinksMapping whether the links endpoint should be registered
	 */
	public WebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints,
			EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration,
			EndpointLinksResolver linksResolver, boolean shouldRegisterLinksMapping) {
		super(endpointMapping, endpoints, endpointMediaTypes, corsConfiguration, shouldRegisterLinksMapping);
		this.linksResolver = linksResolver;
		setOrder(-100);
	}

	@Override
	protected LinksHandler getLinksHandler() {
		return new WebMvcLinksHandler();
	}

	/**
	 * Handler for root endpoint providing links.
	 */
	class WebMvcLinksHandler implements LinksHandler {

		@Override
		@ResponseBody
		public Map<String, Map<String, Link>> links(HttpServletRequest request, HttpServletResponse response) {
			return Collections.singletonMap("_links",
					WebMvcEndpointHandlerMapping.this.linksResolver.resolveLinks(request.getRequestURL().toString()));
		}

		@Override
		public String toString() {
			return "Actuator root web endpoint";
		}

	}

}
