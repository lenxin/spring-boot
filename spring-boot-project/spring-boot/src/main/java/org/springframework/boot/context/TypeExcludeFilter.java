package org.springframework.boot.context;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * Provides exclusion {@link TypeFilter TypeFilters} that are loaded from the
 * {@link BeanFactory} and automatically applied to {@code SpringBootApplication}
 * scanning. Can also be used directly with {@code @ComponentScan} as follows:
 * <pre class="code">
 * &#064;ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
 * </pre>
 * <p>
 * Implementations should provide a subclass registered with {@link BeanFactory} and
 * override the {@link #match(MetadataReader, MetadataReaderFactory)} method. They should
 * also implement a valid {@link #hashCode() hashCode} and {@link #equals(Object) equals}
 * methods so that they can be used as part of Spring test's application context caches.
 * <p>
 * Note that {@code TypeExcludeFilters} are initialized very early in the application
 * lifecycle, they should generally not have dependencies on any other beans. They are
 * primarily used internally to support {@code spring-boot-test}.
 *

 * @since 1.4.0
 */
public class TypeExcludeFilter implements TypeFilter, BeanFactoryAware {

	private BeanFactory beanFactory;

	private Collection<TypeExcludeFilter> delegates;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		if (this.beanFactory instanceof ListableBeanFactory && getClass() == TypeExcludeFilter.class) {
			for (TypeExcludeFilter delegate : getDelegates()) {
				if (delegate.match(metadataReader, metadataReaderFactory)) {
					return true;
				}
			}
		}
		return false;
	}

	private Collection<TypeExcludeFilter> getDelegates() {
		Collection<TypeExcludeFilter> delegates = this.delegates;
		if (delegates == null) {
			delegates = ((ListableBeanFactory) this.beanFactory).getBeansOfType(TypeExcludeFilter.class).values();
			this.delegates = delegates;
		}
		return delegates;
	}

	@Override
	public boolean equals(Object obj) {
		throw new IllegalStateException("TypeExcludeFilter " + getClass() + " has not implemented equals");
	}

	@Override
	public int hashCode() {
		throw new IllegalStateException("TypeExcludeFilter " + getClass() + " has not implemented hashCode");
	}

}
