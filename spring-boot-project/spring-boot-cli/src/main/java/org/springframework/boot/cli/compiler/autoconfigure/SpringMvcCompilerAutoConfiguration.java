package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;
import org.springframework.boot.groovy.GroovyTemplate;

/**
 * {@link CompilerAutoConfiguration} for Spring MVC.
 *


 * @since 1.0.0
 */
public class SpringMvcCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "Controller", "RestController", "EnableWebMvc");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.web.servlet.mvc.Controller")
				.add("spring-boot-starter-web");
		dependencies.ifAnyMissingClasses("groovy.text.TemplateEngine").add("groovy-templates");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addStarImports("org.springframework.web.bind.annotation",
				"org.springframework.web.servlet.config.annotation", "org.springframework.web.servlet",
				"org.springframework.http", "org.springframework.web.servlet.handler", "org.springframework.http",
				"org.springframework.ui", "groovy.text");
		imports.addStaticImport(GroovyTemplate.class.getName(), "template");
	}

}
