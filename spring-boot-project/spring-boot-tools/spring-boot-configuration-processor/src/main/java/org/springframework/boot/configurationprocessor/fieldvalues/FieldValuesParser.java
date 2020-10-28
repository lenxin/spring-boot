package org.springframework.boot.configurationprocessor.fieldvalues;

import java.util.Collections;
import java.util.Map;

import javax.lang.model.element.TypeElement;

import org.springframework.boot.configurationprocessor.fieldvalues.javac.JavaCompilerFieldValuesParser;

/**
 * Parser which can be used to obtain the field values from an {@link TypeElement}.
 *

 * @since 1.1.2
 * @see JavaCompilerFieldValuesParser
 */
@FunctionalInterface
public interface FieldValuesParser {

	/**
	 * Implementation of {@link FieldValuesParser} that always returns an empty result.
	 */
	FieldValuesParser NONE = (element) -> Collections.emptyMap();

	/**
	 * Return the field values for the given element.
	 * @param element the element to inspect
	 * @return a map of field names to values.
	 * @throws Exception if the values cannot be extracted
	 */
	Map<String, Object> getFieldValues(TypeElement element) throws Exception;

}
