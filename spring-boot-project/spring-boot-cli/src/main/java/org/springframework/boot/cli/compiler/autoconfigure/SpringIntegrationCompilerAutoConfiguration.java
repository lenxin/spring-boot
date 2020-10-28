package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring Integration.
 *


 * @since 1.0.0
 */
public class SpringIntegrationCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableIntegration")
				|| AstUtils.hasAtLeastOneAnnotation(classNode, "MessageEndpoint");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.integration.config.EnableIntegration")
				.add("spring-boot-starter-integration");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addImports("org.springframework.messaging.Message", "org.springframework.messaging.MessageChannel",
				"org.springframework.messaging.PollableChannel", "org.springframework.messaging.SubscribableChannel",
				"org.springframework.messaging.MessageHeaders",
				"org.springframework.integration.support.MessageBuilder",
				"org.springframework.integration.channel.DirectChannel",
				"org.springframework.integration.channel.QueueChannel",
				"org.springframework.integration.channel.ExecutorChannel",
				"org.springframework.integration.core.MessagingTemplate",
				"org.springframework.integration.config.EnableIntegration");
		imports.addStarImports("org.springframework.integration.annotation");
	}

}
