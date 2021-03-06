package org.springframework.boot.autoconfigure.domain;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.boot.autoconfigure.domain.scan.a.EmbeddableA;
import org.springframework.boot.autoconfigure.domain.scan.a.EntityA;
import org.springframework.boot.autoconfigure.domain.scan.b.EmbeddableB;
import org.springframework.boot.autoconfigure.domain.scan.b.EntityB;
import org.springframework.boot.autoconfigure.domain.scan.c.EmbeddableC;
import org.springframework.boot.autoconfigure.domain.scan.c.EntityC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link EntityScanner}.
 *

 */
class EntityScannerTests {

	@Test
	void createWhenContextIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new EntityScanner(null))
				.withMessageContaining("Context must not be null");
	}

	@Test
	void scanShouldScanFromSinglePackage() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
		EntityScanner scanner = new EntityScanner(context);
		Set<Class<?>> scanned = scanner.scan(Entity.class);
		assertThat(scanned).containsOnly(EntityA.class, EntityB.class, EntityC.class);
		context.close();
	}

	@Test
	void scanShouldScanFromMultiplePackages() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanAConfig.class,
				ScanBConfig.class);
		EntityScanner scanner = new EntityScanner(context);
		Set<Class<?>> scanned = scanner.scan(Entity.class);
		assertThat(scanned).containsOnly(EntityA.class, EntityB.class);
		context.close();
	}

	@Test
	void scanShouldFilterOnAnnotation() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
		EntityScanner scanner = new EntityScanner(context);
		assertThat(scanner.scan(Entity.class)).containsOnly(EntityA.class, EntityB.class, EntityC.class);
		assertThat(scanner.scan(Embeddable.class)).containsOnly(EmbeddableA.class, EmbeddableB.class,
				EmbeddableC.class);
		assertThat(scanner.scan(Entity.class, Embeddable.class)).containsOnly(EntityA.class, EntityB.class,
				EntityC.class, EmbeddableA.class, EmbeddableB.class, EmbeddableC.class);
		context.close();
	}

	@Test
	void scanShouldUseCustomCandidateComponentProvider() throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider candidateComponentProvider = mock(
				ClassPathScanningCandidateComponentProvider.class);
		given(candidateComponentProvider.findCandidateComponents("org.springframework.boot.autoconfigure.domain.scan"))
				.willReturn(Collections.emptySet());
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
		TestEntityScanner scanner = new TestEntityScanner(context, candidateComponentProvider);
		scanner.scan(Entity.class);
		ArgumentCaptor<AnnotationTypeFilter> annotationTypeFilter = ArgumentCaptor.forClass(AnnotationTypeFilter.class);
		verify(candidateComponentProvider).addIncludeFilter(annotationTypeFilter.capture());
		verify(candidateComponentProvider)
				.findCandidateComponents("org.springframework.boot.autoconfigure.domain.scan");
		verifyNoMoreInteractions(candidateComponentProvider);
		assertThat(annotationTypeFilter.getValue().getAnnotationType()).isEqualTo(Entity.class);
	}

	private static class TestEntityScanner extends EntityScanner {

		private final ClassPathScanningCandidateComponentProvider candidateComponentProvider;

		TestEntityScanner(ApplicationContext context,
				ClassPathScanningCandidateComponentProvider candidateComponentProvider) {
			super(context);
			this.candidateComponentProvider = candidateComponentProvider;
		}

		@Override
		protected ClassPathScanningCandidateComponentProvider createClassPathScanningCandidateComponentProvider(
				ApplicationContext context) {
			return this.candidateComponentProvider;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan("org.springframework.boot.autoconfigure.domain.scan")
	static class ScanConfig {

	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan(basePackageClasses = EntityA.class)
	static class ScanAConfig {

	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan(basePackageClasses = EntityB.class)
	static class ScanBConfig {

	}

}
