package org.springframework.boot.autoconfigure.data.mongo;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MongoReactiveDataAutoConfiguration}.
 *


 */
class MongoReactiveDataAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(PropertyPlaceholderAutoConfiguration.class,
					MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class));

	@Test
	void templateExists() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ReactiveMongoTemplate.class));
	}

	@Test
	void whenNoGridFsDatabaseIsConfiguredTheGridFsTemplateUsesTheMainDatabase() {
		this.contextRunner.run((context) -> assertThat(grisFsTemplateDatabaseName(context)).isEqualTo("test"));
	}

	@Test
	void whenGridFsDatabaseIsConfiguredThenGridFsTemplateUsesIt() {
		this.contextRunner.withPropertyValues("spring.data.mongodb.gridfs.database:grid")
				.run((context) -> assertThat(grisFsTemplateDatabaseName(context)).isEqualTo("grid"));
	}

	@Test
	@Deprecated
	void whenGridFsDatabaseIsConfiguredWithDeprecatedPropertyThenGridFsTemplateUsesIt() {
		this.contextRunner.withPropertyValues("spring.data.mongodb.gridFsDatabase:grid")
				.run((context) -> assertThat(grisFsTemplateDatabaseName(context)).isEqualTo("grid"));
	}

	@Test
	void whenGridFsBucketIsConfiguredThenGridFsTemplateUsesIt() {
		this.contextRunner.withPropertyValues("spring.data.mongodb.gridfs.bucket:test-bucket").run((context) -> {
			assertThat(context).hasSingleBean(ReactiveGridFsTemplate.class);
			ReactiveGridFsTemplate template = context.getBean(ReactiveGridFsTemplate.class);
			assertThat(template).hasFieldOrPropertyWithValue("bucket", "test-bucket");
		});
	}

	@Test
	void backsOffIfMongoClientBeanIsNotPresent() {
		ApplicationContextRunner runner = new ApplicationContextRunner().withConfiguration(AutoConfigurations
				.of(PropertyPlaceholderAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class));
		runner.run((context) -> assertThat(context).doesNotHaveBean(MongoReactiveDataAutoConfiguration.class));
	}

	private String grisFsTemplateDatabaseName(AssertableApplicationContext context) {
		assertThat(context).hasSingleBean(ReactiveGridFsTemplate.class);
		ReactiveGridFsTemplate template = context.getBean(ReactiveGridFsTemplate.class);
		ReactiveMongoDatabaseFactory factory = (ReactiveMongoDatabaseFactory) ReflectionTestUtils.getField(template,
				"dbFactory");
		return factory.getMongoDatabase().block().getName();
	}

}
