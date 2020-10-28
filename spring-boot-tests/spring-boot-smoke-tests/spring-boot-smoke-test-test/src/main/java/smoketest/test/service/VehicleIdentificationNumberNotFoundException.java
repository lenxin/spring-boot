package smoketest.test.service;

import smoketest.test.domain.VehicleIdentificationNumber;

/**
 * Exception thrown when a {@link VehicleIdentificationNumber} is not found.
 *

 */
public class VehicleIdentificationNumberNotFoundException extends RuntimeException {

	private final VehicleIdentificationNumber vehicleIdentificationNumber;

	public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin) {
		this(vin, null);
	}

	public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin, Throwable cause) {
		super("Unable to find VehicleIdentificationNumber " + vin, cause);
		this.vehicleIdentificationNumber = vin;
	}

	public VehicleIdentificationNumber getVehicleIdentificationNumber() {
		return this.vehicleIdentificationNumber;
	}

}
