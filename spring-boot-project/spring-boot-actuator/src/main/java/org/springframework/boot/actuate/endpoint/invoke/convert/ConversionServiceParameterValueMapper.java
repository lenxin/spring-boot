package org.springframework.boot.actuate.endpoint.invoke.convert;

import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.ParameterMappingException;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;

/**
 * {@link ParameterValueMapper} backed by a {@link ConversionService}.
 *


 * @since 2.0.0
 */
public class ConversionServiceParameterValueMapper implements ParameterValueMapper {

	private final ConversionService conversionService;

	/**
	 * Create a new {@link ConversionServiceParameterValueMapper} instance.
	 */
	public ConversionServiceParameterValueMapper() {
		this(ApplicationConversionService.getSharedInstance());
	}

	/**
	 * Create a new {@link ConversionServiceParameterValueMapper} instance backed by a
	 * specific conversion service.
	 * @param conversionService the conversion service
	 */
	public ConversionServiceParameterValueMapper(ConversionService conversionService) {
		Assert.notNull(conversionService, "ConversionService must not be null");
		this.conversionService = conversionService;
	}

	@Override
	public Object mapParameterValue(OperationParameter parameter, Object value) throws ParameterMappingException {
		try {
			return this.conversionService.convert(value, parameter.getType());
		}
		catch (Exception ex) {
			throw new ParameterMappingException(parameter, value, ex);
		}
	}

}
