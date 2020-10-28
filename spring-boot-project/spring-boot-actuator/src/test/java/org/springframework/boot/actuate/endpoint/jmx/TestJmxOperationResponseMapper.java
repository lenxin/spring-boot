package org.springframework.boot.actuate.endpoint.jmx;

/**
 * Test {@link JmxOperationResponseMapper} implementation.
 *

 */
class TestJmxOperationResponseMapper implements JmxOperationResponseMapper {

	@Override
	public Object mapResponse(Object response) {
		return response;
	}

	@Override
	public Class<?> mapResponseType(Class<?> responseType) {
		return responseType;
	}

}
