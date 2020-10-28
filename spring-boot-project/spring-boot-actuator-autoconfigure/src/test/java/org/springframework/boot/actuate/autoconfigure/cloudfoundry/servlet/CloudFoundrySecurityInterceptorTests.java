package org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.AccessLevel;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.CloudFoundryAuthorizationException.Reason;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.SecurityResponse;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.Token;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.Base64Utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CloudFoundrySecurityInterceptor}.
 *

 */
@ExtendWith(MockitoExtension.class)
class CloudFoundrySecurityInterceptorTests {

	@Mock
	private TokenValidator tokenValidator;

	@Mock
	private CloudFoundrySecurityService securityService;

	private CloudFoundrySecurityInterceptor interceptor;

	private MockHttpServletRequest request;

	@BeforeEach
	void setup() {
		this.interceptor = new CloudFoundrySecurityInterceptor(this.tokenValidator, this.securityService, "my-app-id");
		this.request = new MockHttpServletRequest();
	}

	@Test
	void preHandleWhenRequestIsPreFlightShouldReturnTrue() {
		this.request.setMethod("OPTIONS");
		this.request.addHeader(HttpHeaders.ORIGIN, "https://example.com");
		this.request.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET");
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void preHandleWhenTokenIsMissingShouldReturnFalse() {
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(Reason.MISSING_AUTHORIZATION.getStatus());
	}

	@Test
	void preHandleWhenTokenIsNotBearerShouldReturnFalse() {
		this.request.addHeader("Authorization", mockAccessToken());
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(Reason.MISSING_AUTHORIZATION.getStatus());
	}

	@Test
	void preHandleWhenApplicationIdIsNullShouldReturnFalse() {
		this.interceptor = new CloudFoundrySecurityInterceptor(this.tokenValidator, this.securityService, null);
		this.request.addHeader("Authorization", "bearer " + mockAccessToken());
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(Reason.SERVICE_UNAVAILABLE.getStatus());
	}

	@Test
	void preHandleWhenCloudFoundrySecurityServiceIsNullShouldReturnFalse() {
		this.interceptor = new CloudFoundrySecurityInterceptor(this.tokenValidator, null, "my-app-id");
		this.request.addHeader("Authorization", "bearer " + mockAccessToken());
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(Reason.SERVICE_UNAVAILABLE.getStatus());
	}

	@Test
	void preHandleWhenAccessIsNotAllowedShouldReturnFalse() {
		String accessToken = mockAccessToken();
		this.request.addHeader("Authorization", "bearer " + accessToken);
		given(this.securityService.getAccessLevel(accessToken, "my-app-id")).willReturn(AccessLevel.RESTRICTED);
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		assertThat(response.getStatus()).isEqualTo(Reason.ACCESS_DENIED.getStatus());
	}

	@Test
	void preHandleSuccessfulWithFullAccess() {
		String accessToken = mockAccessToken();
		this.request.addHeader("Authorization", "Bearer " + accessToken);
		given(this.securityService.getAccessLevel(accessToken, "my-app-id")).willReturn(AccessLevel.FULL);
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("test"));
		ArgumentCaptor<Token> tokenArgumentCaptor = ArgumentCaptor.forClass(Token.class);
		verify(this.tokenValidator).validate(tokenArgumentCaptor.capture());
		Token token = tokenArgumentCaptor.getValue();
		assertThat(token.toString()).isEqualTo(accessToken);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
		assertThat(this.request.getAttribute("cloudFoundryAccessLevel")).isEqualTo(AccessLevel.FULL);
	}

	@Test
	void preHandleSuccessfulWithRestrictedAccess() {
		String accessToken = mockAccessToken();
		this.request.addHeader("Authorization", "Bearer " + accessToken);
		given(this.securityService.getAccessLevel(accessToken, "my-app-id")).willReturn(AccessLevel.RESTRICTED);
		SecurityResponse response = this.interceptor.preHandle(this.request, EndpointId.of("info"));
		ArgumentCaptor<Token> tokenArgumentCaptor = ArgumentCaptor.forClass(Token.class);
		verify(this.tokenValidator).validate(tokenArgumentCaptor.capture());
		Token token = tokenArgumentCaptor.getValue();
		assertThat(token.toString()).isEqualTo(accessToken);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
		assertThat(this.request.getAttribute("cloudFoundryAccessLevel")).isEqualTo(AccessLevel.RESTRICTED);
	}

	private String mockAccessToken() {
		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0b3B0YWwu"
				+ "Y29tIiwiZXhwIjoxNDI2NDIwODAwLCJhd2Vzb21lIjp0cnVlfQ."
				+ Base64Utils.encodeToString("signature".getBytes());
	}

}
