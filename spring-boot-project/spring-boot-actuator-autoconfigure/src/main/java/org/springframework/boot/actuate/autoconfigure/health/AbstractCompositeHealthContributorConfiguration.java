package org.springframework.boot.actuate.autoconfigure.health;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

/**
 * Base class for health contributor configurations that can combine source beans into a
 * composite.
 *
 * @param <C> the contributor type
 * @param <I> the health indicator type
 * @param <B> the bean type


 * @since 2.2.0
 */
public abstract class AbstractCompositeHealthContributorConfiguration<C, I extends C, B> {

	private final Class<?> indicatorType;

	private final Class<?> beanType;

	AbstractCompositeHealthContributorConfiguration() {
		ResolvableType type = ResolvableType.forClass(AbstractCompositeHealthContributorConfiguration.class,
				getClass());
		this.indicatorType = type.resolveGeneric(1);
		this.beanType = type.resolveGeneric(2);

	}

	protected final C createContributor(Map<String, B> beans) {
		Assert.notEmpty(beans, "Beans must not be empty");
		if (beans.size() == 1) {
			return createIndicator(beans.values().iterator().next());
		}
		return createComposite(beans);
	}

	protected abstract C createComposite(Map<String, B> beans);

	@SuppressWarnings("unchecked")
	protected I createIndicator(B bean) {
		try {
			Constructor<I> constructor = (Constructor<I>) this.indicatorType.getDeclaredConstructor(this.beanType);
			return BeanUtils.instantiateClass(constructor, bean);
		}
		catch (Exception ex) {
			throw new IllegalStateException(
					"Unable to create health indicator " + this.indicatorType + " for bean type " + this.beanType, ex);
		}
	}

}
