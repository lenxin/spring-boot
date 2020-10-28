package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring Batch.
 *


 * @since 1.0.0
 */
public class SpringBatchCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableBatchProcessing");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.batch.core.Job").add("spring-boot-starter-batch");
		dependencies.ifAnyMissingClasses("org.springframework.jdbc.core.JdbcTemplate").add("spring-jdbc");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addImports("org.springframework.batch.repeat.RepeatStatus",
				"org.springframework.batch.core.scope.context.ChunkContext",
				"org.springframework.batch.core.step.tasklet.Tasklet",
				"org.springframework.batch.core.configuration.annotation.StepScope",
				"org.springframework.batch.core.configuration.annotation.JobBuilderFactory",
				"org.springframework.batch.core.configuration.annotation.StepBuilderFactory",
				"org.springframework.batch.core.configuration.annotation.EnableBatchProcessing",
				"org.springframework.batch.core.Step", "org.springframework.batch.core.StepExecution",
				"org.springframework.batch.core.StepContribution", "org.springframework.batch.core.Job",
				"org.springframework.batch.core.JobExecution", "org.springframework.batch.core.JobParameter",
				"org.springframework.batch.core.JobParameters", "org.springframework.batch.core.launch.JobLauncher",
				"org.springframework.batch.core.converter.JobParametersConverter",
				"org.springframework.batch.core.converter.DefaultJobParametersConverter");
	}

}
