package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for the caching infrastructure.
 *

 * @since 1.2.0
 */
public class CachingCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableCaching");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) throws CompilationFailedException {
		dependencies.add("spring-context-support");
	}

	@Override
	public void applyImports(ImportCustomizer imports) throws CompilationFailedException {
		imports.addStarImports("org.springframework.cache", "org.springframework.cache.annotation",
				"org.springframework.cache.concurrent");
	}

}
