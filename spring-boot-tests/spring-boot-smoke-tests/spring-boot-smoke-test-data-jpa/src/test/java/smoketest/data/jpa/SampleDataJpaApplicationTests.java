package smoketest.data.jpa;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test to run the application.
 *


 */
// Enable JMX so we can test the MBeans (you can't do this in a properties file)
@SpringBootTest(properties = "spring.jmx.enabled:true")
@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
class SampleDataJpaApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	void testHome() throws Exception {

		this.mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("Bath"));
	}

	@Test
	void testJmx() throws Exception {
		assertThat(ManagementFactory.getPlatformMBeanServer()
				.queryMBeans(new ObjectName("jpa.sample:type=HikariDataSource,*"), null)).hasSize(1);
	}

}
