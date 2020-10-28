package smoketest.test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smoketest.test.domain.VehicleIdentificationNumber;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * {@link VehicleDetailsService} backed by a remote REST service.
 *

 */
@Service
public class RemoteVehicleDetailsService implements VehicleDetailsService {

	private static final Log logger = LogFactory.getLog(RemoteVehicleDetailsService.class);

	private final RestTemplate restTemplate;

	public RemoteVehicleDetailsService(ServiceProperties properties, RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.rootUri(properties.getVehicleServiceRootUrl()).build();
	}

	@Override
	public VehicleDetails getVehicleDetails(VehicleIdentificationNumber vin)
			throws VehicleIdentificationNumberNotFoundException {
		Assert.notNull(vin, "VIN must not be null");
		logger.debug("Retrieving vehicle data for: " + vin);
		try {
			return this.restTemplate.getForObject("/vehicle/{vin}/details", VehicleDetails.class, vin);
		}
		catch (HttpStatusCodeException ex) {
			if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
				throw new VehicleIdentificationNumberNotFoundException(vin, ex);
			}
			throw ex;
		}
	}

}
