package org.springframework.boot.cli.compiler;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * Smart extension of {@link ImportCustomizer} that will only add a specific import if a
 * class with the same name is not already explicitly imported.
 *

 */
class SmartImportCustomizer extends ImportCustomizer {

	private SourceUnit source;

	SmartImportCustomizer(SourceUnit source) {
		this.source = source;
	}

	@Override
	public ImportCustomizer addImport(String alias, String className) {
		if (this.source.getAST().getImport(ClassHelper.make(className).getNameWithoutPackage()) == null) {
			super.addImport(alias, className);
		}
		return this;
	}

	@Override
	public ImportCustomizer addImports(String... imports) {
		for (String alias : imports) {
			if (this.source.getAST().getImport(ClassHelper.make(alias).getNameWithoutPackage()) == null) {
				super.addImports(alias);
			}
		}
		return this;
	}

}
