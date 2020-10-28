package org.springframework.boot.convert;

/**
 * Converter to support mapping of YAML style {@code "false"} and {@code "true"} to enums
 * {@code ON} and {@code OFF}.
 *

 */
final class LenientBooleanToEnumConverterFactory extends LenientObjectToEnumConverterFactory<Boolean> {

}
