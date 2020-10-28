package org.springframework.boot.cli.compiler;

import org.codehaus.groovy.transform.ASTTransformation;

/**
 * Marker interface for AST transformations that should be installed automatically from
 * {@code META-INF/services}.
 *

 * @since 1.0.0
 */
@FunctionalInterface
public interface SpringBootAstTransformation extends ASTTransformation {

}
