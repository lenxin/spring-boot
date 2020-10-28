package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.discovery.DiscoverySelectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.testsupport.junit.platform.Launcher;
import org.springframework.boot.testsupport.junit.platform.LauncherDiscoveryRequest;
import org.springframework.boot.testsupport.junit.platform.LauncherDiscoveryRequestBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} default print output.
 *


 */
@ExtendWith(OutputCaptureExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class WebMvcTestPrintDefaultIntegrationTests {

	@Test
	void shouldNotPrint(CapturedOutput output) throws Throwable {
		executeTests(ShouldNotPrint.class);
		assertThat(output).doesNotContain("HTTP Method");
	}

	@Test
	void shouldPrint(CapturedOutput output) throws Throwable {
		executeTests(ShouldPrint.class);
		assertThat(output).containsOnlyOnce("HTTP Method");
	}

	private void executeTests(Class<?> testClass) throws Throwable {
		ClassLoader classLoader = testClass.getClassLoader();
		LauncherDiscoveryRequest request = new LauncherDiscoveryRequestBuilder(classLoader)
				.selectors(DiscoverySelectors.selectClass(testClass)).build();
		Launcher launcher = new Launcher(testClass.getClassLoader());
		launcher.execute(request);
	}

	@WebMvcTest
	@WithMockUser
	@AutoConfigureMockMvc
	static class ShouldNotPrint {

		@Autowired
		private MockMvc mvc;

		@Test
		void test() throws Exception {
			this.mvc.perform(get("/one")).andExpect(content().string("one")).andExpect(status().isOk());
		}

	}

	@WebMvcTest
	@WithMockUser
	@AutoConfigureMockMvc
	static class ShouldPrint {

		@Autowired
		private MockMvc mvc;

		@Test
		void test() throws Exception {
			this.mvc.perform(get("/one")).andExpect(content().string("none")).andExpect(status().isOk());
		}

	}

}
