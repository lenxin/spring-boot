package org.springframework.boot.autoconfigure.data.neo4j;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.data.neo4j.core.ReactiveNeo4jOperations;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Neo4jReactiveDataAutoConfiguration}.
 *


 */
class Neo4jReactiveDataAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(MockedDriverConfiguration.class)
			.withConfiguration(AutoConfigurations.of(Neo4jAutoConfiguration.class, Neo4jDataAutoConfiguration.class,
					Neo4jReactiveDataAutoConfiguration.class));

	@Test
	void shouldProvideDefaultDatabaseNameProvider() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(ReactiveDatabaseSelectionProvider.class);
			assertThat(context.getBean(ReactiveDatabaseSelectionProvider.class))
					.isSameAs(ReactiveDatabaseSelectionProvider.getDefaultSelectionProvider());
		});
	}

	@Test
	void shouldUseDatabaseNameIfSet() {
		this.contextRunner.withPropertyValues("spring.data.neo4j.database=test").run((context) -> {
			assertThat(context).hasSingleBean(ReactiveDatabaseSelectionProvider.class);
			StepVerifier.create(context.getBean(ReactiveDatabaseSelectionProvider.class).getDatabaseSelection())
					.consumeNextWith((databaseSelection) -> assertThat(databaseSelection.getValue()).isEqualTo("test"))
					.expectComplete();
		});
	}

	@Test
	void shouldReuseExistingDatabaseNameProvider() {
		this.contextRunner.withPropertyValues("spring.data.neo4j.database=ignored")
				.withUserConfiguration(CustomReactiveDatabaseSelectionProviderConfiguration.class).run((context) -> {
					assertThat(context).hasSingleBean(ReactiveDatabaseSelectionProvider.class);
					StepVerifier.create(context.getBean(ReactiveDatabaseSelectionProvider.class).getDatabaseSelection())
							.consumeNextWith(
									(databaseSelection) -> assertThat(databaseSelection.getValue()).isEqualTo("custom"))
							.expectComplete();
				});
	}

	@Test
	void shouldProvideReactiveNeo4jClient() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ReactiveNeo4jClient.class));
	}

	@Test
	void shouldReuseExistingReactiveNeo4jClient() {
		this.contextRunner
				.withBean("myCustomReactiveClient", ReactiveNeo4jClient.class, () -> mock(ReactiveNeo4jClient.class))
				.run((context) -> assertThat(context).hasSingleBean(ReactiveNeo4jClient.class)
						.hasBean("myCustomReactiveClient"));
	}

	@Test
	void shouldProvideReactiveNeo4jTemplate() {
		this.contextRunner.withUserConfiguration(CustomReactiveDatabaseSelectionProviderConfiguration.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(ReactiveNeo4jTemplate.class);
					assertThat(context.getBean(ReactiveNeo4jTemplate.class)).extracting("databaseSelectionProvider")
							.isSameAs(context.getBean(ReactiveDatabaseSelectionProvider.class));
				});
	}

	@Test
	void shouldReuseExistingReactiveNeo4jTemplate() {
		this.contextRunner
				.withBean("myCustomReactiveOperations", ReactiveNeo4jOperations.class,
						() -> mock(ReactiveNeo4jOperations.class))
				.run((context) -> assertThat(context).hasSingleBean(ReactiveNeo4jOperations.class)
						.hasBean("myCustomReactiveOperations"));
	}

	@Test
	void shouldUseExistingReactiveTransactionManager() {
		this.contextRunner
				.withBean("myCustomReactiveTransactionManager", ReactiveTransactionManager.class,
						() -> mock(ReactiveTransactionManager.class))
				.run((context) -> assertThat(context).hasSingleBean(ReactiveTransactionManager.class)
						.hasSingleBean(TransactionManager.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomReactiveDatabaseSelectionProviderConfiguration {

		@Bean
		ReactiveDatabaseSelectionProvider databaseNameProvider() {
			return () -> Mono.just(DatabaseSelection.byName("custom"));
		}

	}

}
