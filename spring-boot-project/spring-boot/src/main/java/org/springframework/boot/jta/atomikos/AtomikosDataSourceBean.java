package org.springframework.boot.jta.atomikos;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Spring friendly version of {@link com.atomikos.jdbc.AtomikosDataSourceBean}.
 *


 * @since 1.2.0
 */
@SuppressWarnings("serial")
@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource")
public class AtomikosDataSourceBean extends com.atomikos.jdbc.AtomikosDataSourceBean
		implements BeanNameAware, InitializingBean, DisposableBean {

	private String beanName;

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.hasLength(getUniqueResourceName())) {
			setUniqueResourceName(this.beanName);
		}
		init();
	}

	@Override
	public void destroy() throws Exception {
		close();
	}

}
