package org.springframework.boot.autoconfigure.hazelcast;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HazelcastAutoConfiguration} with full classpath.
 *

 */
class HazelcastAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(HazelcastAutoConfiguration.class));

	@Test
	void defaultConfigFile() {
		// no hazelcast-client.xml and hazelcast.xml is present in root classpath
		// this also asserts that XML has priority over YAML
		// as both hazelcast.yaml and hazelcast.xml in test classpath.
		this.contextRunner.run((context) -> {
			Config config = context.getBean(HazelcastInstance.class).getConfig();
			assertThat(config.getConfigurationUrl()).isEqualTo(new ClassPathResource("hazelcast.xml").getURL());
		});
	}

	@Test
	void hazelcastInstanceNotCreatedWhenJetIsPresent() {
		this.contextRunner.withClassLoader(new JetConfigClassLoader())
				.run((context) -> assertThat(context).doesNotHaveBean(HazelcastInstance.class));
	}

	/**
	 * A test {@link URLClassLoader} that emulates the default Hazelcast Jet configuration
	 * file exists on the classpath.
	 */
	static class JetConfigClassLoader extends URLClassLoader {

		private static final Resource FALLBACK = new ClassPathResource("hazelcast.yaml");

		JetConfigClassLoader() {
			super(new URL[0], JetConfigClassLoader.class.getClassLoader());
		}

		@Override
		public URL getResource(String name) {
			if (name.equals("hazelcast-jet-default.yaml")) {
				return getEmulatedJetConfigUrl();
			}
			return super.getResource(name);
		}

		private URL getEmulatedJetConfigUrl() {
			try {
				return FALLBACK.getURL();
			}
			catch (IOException ex) {
				throw new IllegalArgumentException(ex);
			}
		}

	}

}
