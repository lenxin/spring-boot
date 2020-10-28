package org.springframework.boot.configurationsample.generic;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Properties with unresolved generic information.
 *
 * @param <B> mapping key type
 * @param <C> mapping value type

 */
@ConfigurationProperties("generic")
public class UnresolvedGenericProperties<B extends Number, C> extends AbstractGenericProperties<String, B, C> {

}
