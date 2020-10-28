package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Settings to apply when configuring Hibernate.
 *

 * @since 2.0.0
 */
public class HibernateSettings {

	private Supplier<String> ddlAuto;

	private Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

	public HibernateSettings ddlAuto(Supplier<String> ddlAuto) {
		this.ddlAuto = ddlAuto;
		return this;
	}

	public String getDdlAuto() {
		return (this.ddlAuto != null) ? this.ddlAuto.get() : null;
	}

	public HibernateSettings hibernatePropertiesCustomizers(
			Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
		this.hibernatePropertiesCustomizers = new ArrayList<>(hibernatePropertiesCustomizers);
		return this;
	}

	public Collection<HibernatePropertiesCustomizer> getHibernatePropertiesCustomizers() {
		return this.hibernatePropertiesCustomizers;
	}

}
