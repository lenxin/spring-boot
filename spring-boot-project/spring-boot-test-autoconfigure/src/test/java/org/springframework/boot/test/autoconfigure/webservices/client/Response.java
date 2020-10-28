package org.springframework.boot.test.autoconfigure.webservices.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Test response.
 *

 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
class Response {

	private int status;

	int getStatus() {
		return this.status;
	}

}
