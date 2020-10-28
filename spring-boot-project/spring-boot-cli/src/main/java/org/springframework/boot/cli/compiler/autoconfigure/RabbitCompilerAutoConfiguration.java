package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring Rabbit.
 *


 * @since 1.0.0
 */
public class RabbitCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableRabbit")
				|| AstUtils.hasAtLeastOneAnnotation(classNode, "EnableRabbitMessaging");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) throws CompilationFailedException {
		dependencies.add("spring-rabbit");

	}

	@Override
	public void applyImports(ImportCustomizer imports) throws CompilationFailedException {
		imports.addStarImports("org.springframework.amqp.rabbit.annotation", "org.springframework.amqp.rabbit.core",
				"org.springframework.amqp.rabbit.config", "org.springframework.amqp.rabbit.connection",
				"org.springframework.amqp.rabbit.listener", "org.springframework.amqp.rabbit.listener.adapter",
				"org.springframework.amqp.core");
	}

}
