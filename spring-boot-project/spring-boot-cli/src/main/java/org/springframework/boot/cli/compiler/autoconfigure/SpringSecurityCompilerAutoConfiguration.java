package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring Security.
 *

 * @since 1.0.0
 */
public class SpringSecurityCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableWebSecurity", "EnableGlobalMethodSecurity");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses(
				"org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity")
				.add("spring-boot-starter-security");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addImports("org.springframework.security.core.Authentication",
				"org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity",
				"org.springframework.security.core.authority.AuthorityUtils")
				.addStarImports("org.springframework.security.config.annotation.web.configuration",
						"org.springframework.security.authentication",
						"org.springframework.security.config.annotation.web",
						"org.springframework.security.config.annotation.web.builders");
	}

}
