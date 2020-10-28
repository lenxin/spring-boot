package org.springframework.boot.test.autoconfigure.data.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Example entity used with {@link DataR2dbcTest} tests.
 *

 */
@Table
public class Example {

	@Id
	String id;

}
