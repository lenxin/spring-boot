package org.springframework.boot.configurationprocessor.fieldvalues.javac;

/**
 * Reflection base alternative for {@code com.sun.source.tree.TreeVisitor}.
 *

 */
interface TreeVisitor {

	void visitVariable(VariableTree variable) throws Exception;

}
