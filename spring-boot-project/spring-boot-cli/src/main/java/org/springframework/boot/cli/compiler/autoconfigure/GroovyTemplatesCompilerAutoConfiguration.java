package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;
import org.springframework.boot.groovy.EnableGroovyTemplates;
import org.springframework.boot.groovy.GroovyTemplate;

/**
 * {@link CompilerAutoConfiguration} for Groovy Templates (outside MVC).
 *

 * @since 1.1.0
 */
public class GroovyTemplatesCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableGroovyTemplates");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("groovy.text.TemplateEngine").add("groovy-templates");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addStarImports("groovy.text");
		imports.addImports(EnableGroovyTemplates.class.getCanonicalName());
		imports.addStaticImport(GroovyTemplate.class.getName(), "template");
	}

}
