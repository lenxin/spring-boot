package org.springframework.boot.build.mavenplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import org.springframework.boot.build.mavenplugin.PluginXmlParser.Plugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link PluginXmlParser}.
 *


 */
public class PluginXmlParserTests {

	private final PluginXmlParser parser = new PluginXmlParser();

	@Test
	void parseExistingDescriptorReturnPluginDescriptor() {
		Plugin plugin = this.parser.parse(new File("src/test/resources/plugin.xml"));
		assertThat(plugin.getGroupId()).isEqualTo("org.springframework.boot");
		assertThat(plugin.getArtifactId()).isEqualTo("spring-boot-maven-plugin");
		assertThat(plugin.getVersion()).isEqualTo("2.2.0.GRADLE-SNAPSHOT");
		assertThat(plugin.getGoalPrefix()).isEqualTo("spring-boot");
		assertThat(plugin.getMojos().stream().map(PluginXmlParser.Mojo::getGoal).collect(Collectors.toList()))
				.containsExactly("build-info", "help", "repackage", "run", "start", "stop");
	}

	@Test
	void parseNonExistingFileThrowException() {
		assertThatThrownBy(() -> this.parser.parse(new File("src/test/resources/nonexistent.xml")))
				.isInstanceOf(RuntimeException.class).hasCauseInstanceOf(FileNotFoundException.class);
	}

}
