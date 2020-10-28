package org.springframework.boot.actuate.autoconfigure.metrics.export.elastic;

import io.micrometer.elastic.ElasticConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link ElasticProperties} to an {@link ElasticConfig}.
 *

 */
class ElasticPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<ElasticProperties>
		implements ElasticConfig {

	ElasticPropertiesConfigAdapter(ElasticProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.elastic";
	}

	@Override
	public String host() {
		return get(ElasticProperties::getHost, ElasticConfig.super::host);
	}

	@Override
	public String index() {
		return get(ElasticProperties::getIndex, ElasticConfig.super::index);
	}

	@Override
	public String indexDateFormat() {
		return get(ElasticProperties::getIndexDateFormat, ElasticConfig.super::indexDateFormat);
	}

	@Override
	public String indexDateSeparator() {
		return get(ElasticProperties::getIndexDateSeparator, ElasticConfig.super::indexDateSeparator);
	}

	@Override
	public String timestampFieldName() {
		return get(ElasticProperties::getTimestampFieldName, ElasticConfig.super::timestampFieldName);
	}

	@Override
	public boolean autoCreateIndex() {
		return get(ElasticProperties::isAutoCreateIndex, ElasticConfig.super::autoCreateIndex);
	}

	@Override
	public String userName() {
		return get(ElasticProperties::getUserName, ElasticConfig.super::userName);
	}

	@Override
	public String password() {
		return get(ElasticProperties::getPassword, ElasticConfig.super::password);
	}

	@Override
	public String pipeline() {
		return get(ElasticProperties::getPipeline, ElasticConfig.super::pipeline);
	}

}
