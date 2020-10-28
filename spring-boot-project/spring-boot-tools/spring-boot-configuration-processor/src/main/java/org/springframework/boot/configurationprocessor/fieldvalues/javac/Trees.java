package org.springframework.boot.configurationprocessor.fieldvalues.javac;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Reflection based access to {@code com.sun.source.util.Trees}.
 *

 */
final class Trees extends ReflectionWrapper {

	private Trees(Object instance) {
		super("com.sun.source.util.Trees", instance);
	}

	Tree getTree(Element element) throws Exception {
		Object tree = findMethod("getTree", Element.class).invoke(getInstance(), element);
		return (tree != null) ? new Tree(tree) : null;
	}

	static Trees instance(ProcessingEnvironment env) throws Exception {
		try {
			ClassLoader classLoader = env.getClass().getClassLoader();
			Class<?> type = findClass(classLoader, "com.sun.source.util.Trees");
			Method method = findMethod(type, "instance", ProcessingEnvironment.class);
			return new Trees(method.invoke(null, env));
		}
		catch (Exception ex) {
			return instance(unwrap(env));
		}
	}

	private static ProcessingEnvironment unwrap(ProcessingEnvironment wrapper) throws Exception {
		Field delegateField = wrapper.getClass().getDeclaredField("delegate");
		delegateField.setAccessible(true);
		return (ProcessingEnvironment) delegateField.get(wrapper);
	}

}
