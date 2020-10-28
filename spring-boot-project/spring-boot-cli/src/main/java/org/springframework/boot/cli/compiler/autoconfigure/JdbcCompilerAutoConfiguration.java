package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring JDBC.
 *

 * @since 1.0.0
 */
public class JdbcCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneFieldOrMethod(classNode, "JdbcTemplate", "NamedParameterJdbcTemplate",
				"DataSource");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.jdbc.core.JdbcTemplate").add("spring-boot-starter-jdbc");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addStarImports("org.springframework.jdbc.core", "org.springframework.jdbc.core.namedparam");
		imports.addImports("javax.sql.DataSource");
	}

}
