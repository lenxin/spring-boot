package org.springframework.boot.configurationsample.generic;

/**
 * An intermediate layer that resolves some of the generics from the parent but not all.
 *
 * @param <C> mapping value type

 */
public abstract class AbstractIntermediateGenericProperties<C> extends AbstractGenericProperties<String, Integer, C> {

}
