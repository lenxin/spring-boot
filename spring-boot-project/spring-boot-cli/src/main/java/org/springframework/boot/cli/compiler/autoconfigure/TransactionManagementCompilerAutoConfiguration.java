package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring MVC.
 *


 * @since 1.0.0
 */
public class TransactionManagementCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableTransactionManagement");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.transaction.annotation.Transactional").add("spring-tx",
				"spring-boot-starter-aop");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addStarImports("org.springframework.transaction.annotation", "org.springframework.transaction.support");
		imports.addImports("org.springframework.transaction.PlatformTransactionManager",
				"org.springframework.transaction.support.AbstractPlatformTransactionManager");
	}

}
