package org.springframework.boot.autoconfigureprocessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * Utilities for dealing with {@link Element} classes.
 *

 */
final class Elements {

	private Elements() {
	}

	static String getQualifiedName(Element element) {
		if (element != null) {
			TypeElement enclosingElement = getEnclosingTypeElement(element.asType());
			if (enclosingElement != null) {
				return getQualifiedName(enclosingElement) + "$"
						+ ((DeclaredType) element.asType()).asElement().getSimpleName().toString();
			}
			if (element instanceof TypeElement) {
				return ((TypeElement) element).getQualifiedName().toString();
			}
		}
		return null;
	}

	private static TypeElement getEnclosingTypeElement(TypeMirror type) {
		if (type instanceof DeclaredType) {
			DeclaredType declaredType = (DeclaredType) type;
			Element enclosingElement = declaredType.asElement().getEnclosingElement();
			if (enclosingElement instanceof TypeElement) {
				return (TypeElement) enclosingElement;
			}
		}
		return null;
	}

}
