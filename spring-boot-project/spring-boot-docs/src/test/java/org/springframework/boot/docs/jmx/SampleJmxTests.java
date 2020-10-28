package org.springframework.boot.docs.jmx;

import javax.management.MBeanServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Example integration test that uses JMX.
 *

 */
@SuppressWarnings("unused")
// tag::test[]
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.jmx.enabled=true")
@DirtiesContext
class SampleJmxTests {

	@Autowired
	private MBeanServer mBeanServer;

	@Test
	void exampleTest() {
		// ...
	}

}
// end::test[]
