package org.springframework.boot.gradle.tasks.bundling;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link LaunchScriptConfiguration}.
 *

 */
class LaunchScriptConfigurationTests {

	private final AbstractArchiveTask task = mock(AbstractArchiveTask.class);

	private final Project project = mock(Project.class);

	@BeforeEach
	void setUp() {
		given(this.task.getProject()).willReturn(this.project);
	}

	@Test
	void initInfoProvidesUsesArchiveBaseNameByDefault() {
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoProvides",
				"base-name");
	}

	@Test
	void initInfoShortDescriptionUsesDescriptionByDefault() {
		given(this.project.getDescription()).willReturn("Project description");
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoShortDescription",
				"Project description");
	}

	@Test
	void initInfoShortDescriptionUsesArchiveBaseNameWhenDescriptionIsNull() {
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoShortDescription",
				"base-name");
	}

	@Test
	void initInfoShortDescriptionUsesSingleLineVersionOfMultiLineProjectDescription() {
		given(this.project.getDescription()).willReturn("Project\ndescription");
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoShortDescription",
				"Project description");
	}

	@Test
	void initInfoDescriptionUsesArchiveBaseNameWhenDescriptionIsNull() {
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoDescription",
				"base-name");
	}

	@Test
	void initInfoDescriptionUsesProjectDescriptionByDefault() {
		given(this.project.getDescription()).willReturn("Project description");
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoDescription",
				"Project description");
	}

	@Test
	void initInfoDescriptionUsesCorrectlyFormattedMultiLineProjectDescription() {
		given(this.project.getDescription()).willReturn("The\nproject\ndescription");
		Property<String> baseName = stringProperty("base-name");
		given(this.task.getArchiveBaseName()).willReturn(baseName);
		assertThat(new LaunchScriptConfiguration(this.task).getProperties()).containsEntry("initInfoDescription",
				"The\n#  project\n#  description");
	}

	@SuppressWarnings("unchecked")
	private Property<String> stringProperty(String value) {
		Property<String> property = mock(Property.class);
		given(property.get()).willReturn(value);
		return property;
	}

}
