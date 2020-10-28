package smoketest.test.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for the service.
 *

 */
@ConfigurationProperties
public class ServiceProperties {

	private String vehicleServiceRootUrl = "http://localhost:8080/vs";

	public String getVehicleServiceRootUrl() {
		return this.vehicleServiceRootUrl;
	}

	public void setVehicleServiceRootUrl(String vehicleServiceRootUrl) {
		this.vehicleServiceRootUrl = vehicleServiceRootUrl;
	}

}
