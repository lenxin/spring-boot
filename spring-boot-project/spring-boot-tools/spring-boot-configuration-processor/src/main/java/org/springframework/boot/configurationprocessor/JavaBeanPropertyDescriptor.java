package org.springframework.boot.configurationprocessor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * A {@link PropertyDescriptor} for a standard JavaBean property.
 *

 */
class JavaBeanPropertyDescriptor extends PropertyDescriptor<ExecutableElement> {

	JavaBeanPropertyDescriptor(TypeElement ownerElement, ExecutableElement factoryMethod, ExecutableElement getter,
			String name, TypeMirror type, VariableElement field, ExecutableElement setter) {
		super(ownerElement, factoryMethod, getter, name, type, field, getter, setter);
	}

	@Override
	protected boolean isProperty(MetadataGenerationEnvironment env) {
		boolean isCollection = env.getTypeUtils().isCollectionOrMap(getType());
		return !env.isExcluded(getType()) && getGetter() != null && (getSetter() != null || isCollection);
	}

	@Override
	protected Object resolveDefaultValue(MetadataGenerationEnvironment environment) {
		return environment.getFieldDefaultValue(getOwnerElement(), getName());
	}

}
