package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring JMS.
 *


 * @since 1.0.0
 */
public class JmsCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableJms")
				|| AstUtils.hasAtLeastOneAnnotation(classNode, "EnableJmsMessaging");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) throws CompilationFailedException {
		dependencies.add("spring-jms", "javax.jms-api");
	}

	@Override
	public void applyImports(ImportCustomizer imports) throws CompilationFailedException {
		imports.addStarImports("javax.jms", "org.springframework.jms.annotation", "org.springframework.jms.config",
				"org.springframework.jms.core", "org.springframework.jms.listener",
				"org.springframework.jms.listener.adapter");
	}

}
