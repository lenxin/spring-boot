package org.springframework.boot.autoconfigure.data.neo4j;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jOperations;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Neo4jDataAutoConfiguration}.
 *






 */
class Neo4jDataAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(MockedDriverConfiguration.class)
			.withConfiguration(AutoConfigurations.of(Neo4jAutoConfiguration.class, Neo4jDataAutoConfiguration.class));

	@Test
	void shouldProvideConversions() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(Neo4jConversions.class));
	}

	@Test
	void shouldProvideDefaultDatabaseNameProvider() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(DatabaseSelectionProvider.class);
			assertThat(context.getBean(DatabaseSelectionProvider.class))
					.isSameAs(DatabaseSelectionProvider.getDefaultSelectionProvider());
		});
	}

	@Test
	void shouldUseDatabaseNameIfSet() {
		this.contextRunner.withPropertyValues("spring.data.neo4j.database=test").run((context) -> {
			assertThat(context).hasSingleBean(DatabaseSelectionProvider.class);
			assertThat(context.getBean(DatabaseSelectionProvider.class).getDatabaseSelection())
					.isEqualTo(DatabaseSelection.byName("test"));
		});
	}

	@Test
	void shouldReuseExistingDatabaseNameProvider() {
		this.contextRunner.withPropertyValues("spring.data.neo4j.database=ignored")
				.withUserConfiguration(CustomDatabaseSelectionProviderConfiguration.class).run((context) -> {
					assertThat(context).hasSingleBean(DatabaseSelectionProvider.class);
					assertThat(context.getBean(DatabaseSelectionProvider.class).getDatabaseSelection())
							.isEqualTo(DatabaseSelection.byName("custom"));
				});
	}

	@Test
	void shouldProvideNeo4jClient() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(Neo4jClient.class));
	}

	@Test
	void shouldReuseExistingNeo4jClient() {
		this.contextRunner.withBean("myCustomClient", Neo4jClient.class, () -> mock(Neo4jClient.class))
				.run((context) -> assertThat(context).hasSingleBean(Neo4jClient.class).hasBean("myCustomClient"));
	}

	@Test
	void shouldProvideNeo4jTemplate() {
		this.contextRunner.withUserConfiguration(CustomDatabaseSelectionProviderConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(Neo4jTemplate.class);
			assertThat(context.getBean(Neo4jTemplate.class)).extracting("databaseSelectionProvider")
					.isSameAs(context.getBean(DatabaseSelectionProvider.class));
		});
	}

	@Test
	void shouldReuseExistingNeo4jTemplate() {
		this.contextRunner.withBean("myCustomOperations", Neo4jOperations.class, () -> mock(Neo4jOperations.class)).run(
				(context) -> assertThat(context).hasSingleBean(Neo4jOperations.class).hasBean("myCustomOperations"));
	}

	@Test
	void shouldProvideTransactionManager() {
		this.contextRunner.withUserConfiguration(CustomDatabaseSelectionProviderConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(Neo4jTransactionManager.class);
			assertThat(context.getBean(Neo4jTransactionManager.class)).extracting("databaseSelectionProvider")
					.isSameAs(context.getBean(DatabaseSelectionProvider.class));
		});
	}

	@Test
	void shouldBackoffIfReactiveTransactionManagerIsSet() {
		this.contextRunner.withBean(ReactiveTransactionManager.class, () -> mock(ReactiveTransactionManager.class))
				.run((context) -> assertThat(context).doesNotHaveBean(Neo4jTransactionManager.class)
						.hasSingleBean(TransactionManager.class));
	}

	@Test
	void shouldReuseExistingTransactionManager() {
		this.contextRunner
				.withBean("myCustomTransactionManager", PlatformTransactionManager.class,
						() -> mock(PlatformTransactionManager.class))
				.run((context) -> assertThat(context).hasSingleBean(PlatformTransactionManager.class)
						.hasBean("myCustomTransactionManager"));
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomDatabaseSelectionProviderConfiguration {

		@Bean
		DatabaseSelectionProvider databaseSelectionProvider() {
			return () -> DatabaseSelection.byName("custom");
		}

	}

}
