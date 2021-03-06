package org.springframework.boot.actuate.autoconfigure.cloudfoundry.reactive;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.CloudFoundryAuthorizationException;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.CloudFoundryAuthorizationException.Reason;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.SecurityResponse;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * Security interceptor to validate the cloud foundry token.
 *

 */
class CloudFoundrySecurityInterceptor {

	private static final Log logger = LogFactory.getLog(CloudFoundrySecurityInterceptor.class);

	private final ReactiveTokenValidator tokenValidator;

	private final ReactiveCloudFoundrySecurityService cloudFoundrySecurityService;

	private final String applicationId;

	private static final Mono<SecurityResponse> SUCCESS = Mono.just(SecurityResponse.success());

	CloudFoundrySecurityInterceptor(ReactiveTokenValidator tokenValidator,
			ReactiveCloudFoundrySecurityService cloudFoundrySecurityService, String applicationId) {
		this.tokenValidator = tokenValidator;
		this.cloudFoundrySecurityService = cloudFoundrySecurityService;
		this.applicationId = applicationId;
	}

	Mono<SecurityResponse> preHandle(ServerWebExchange exchange, String id) {
		ServerHttpRequest request = exchange.getRequest();
		if (CorsUtils.isPreFlightRequest(request)) {
			return SUCCESS;
		}
		if (!StringUtils.hasText(this.applicationId)) {
			return Mono.error(new CloudFoundryAuthorizationException(Reason.SERVICE_UNAVAILABLE,
					"Application id is not available"));
		}
		if (this.cloudFoundrySecurityService == null) {
			return Mono.error(new CloudFoundryAuthorizationException(Reason.SERVICE_UNAVAILABLE,
					"Cloud controller URL is not available"));
		}
		return check(exchange, id).then(SUCCESS).doOnError(this::logError).onErrorResume(this::getErrorResponse);
	}

	private void logError(Throwable ex) {
		logger.error(ex.getMessage(), ex);
	}

	private Mono<Void> check(ServerWebExchange exchange, String id) {
		try {
			Token token = getToken(exchange.getRequest());
			return this.tokenValidator.validate(token)
					.then(this.cloudFoundrySecurityService.getAccessLevel(token.toString(), this.applicationId))
					.filter((accessLevel) -> accessLevel.isAccessAllowed(id))
					.switchIfEmpty(
							Mono.error(new CloudFoundryAuthorizationException(Reason.ACCESS_DENIED, "Access denied")))
					.doOnSuccess((accessLevel) -> exchange.getAttributes().put("cloudFoundryAccessLevel", accessLevel))
					.then();
		}
		catch (CloudFoundryAuthorizationException ex) {
			return Mono.error(ex);
		}
	}

	private Mono<SecurityResponse> getErrorResponse(Throwable throwable) {
		if (throwable instanceof CloudFoundryAuthorizationException) {
			CloudFoundryAuthorizationException cfException = (CloudFoundryAuthorizationException) throwable;
			return Mono.just(new SecurityResponse(cfException.getStatusCode(),
					"{\"security_error\":\"" + cfException.getMessage() + "\"}"));
		}
		return Mono.just(new SecurityResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage()));
	}

	private Token getToken(ServerHttpRequest request) {
		String authorization = request.getHeaders().getFirst("Authorization");
		String bearerPrefix = "bearer ";
		if (authorization == null || !authorization.toLowerCase(Locale.ENGLISH).startsWith(bearerPrefix)) {
			throw new CloudFoundryAuthorizationException(Reason.MISSING_AUTHORIZATION,
					"Authorization header is missing or invalid");
		}
		return new Token(authorization.substring(bearerPrefix.length()));
	}

}
