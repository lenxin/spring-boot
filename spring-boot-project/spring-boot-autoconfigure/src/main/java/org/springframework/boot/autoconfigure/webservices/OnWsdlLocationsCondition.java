package org.springframework.boot.autoconfigure.webservices;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.OnPropertyListCondition;

/**
 * Condition to determine if {@code spring.webservices.wsdl-locations} is specified.
 *


 */
class OnWsdlLocationsCondition extends OnPropertyListCondition {

	OnWsdlLocationsCondition() {
		super("spring.webservices.wsdl-locations", () -> ConditionMessage.forCondition("WSDL locations"));
	}

}
