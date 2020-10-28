package org.springframework.boot.actuate.endpoint;

/**
 * An enumeration of the different types of operation supported by an endpoint.
 *

 * @since 2.0.0
 * @see Operation
 */
public enum OperationType {

	/**
	 * A read operation.
	 */
	READ,

	/**
	 * A write operation.
	 */
	WRITE,

	/**
	 * A delete operation.
	 */
	DELETE

}
