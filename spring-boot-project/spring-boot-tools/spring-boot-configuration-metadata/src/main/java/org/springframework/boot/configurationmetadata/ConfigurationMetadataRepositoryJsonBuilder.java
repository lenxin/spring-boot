package org.springframework.boot.configurationmetadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Load a {@link ConfigurationMetadataRepository} from the content of arbitrary
 * resource(s).
 *

 * @since 1.3.0
 */
public final class ConfigurationMetadataRepositoryJsonBuilder {

	private Charset defaultCharset = StandardCharsets.UTF_8;

	private final JsonReader reader = new JsonReader();

	private final List<SimpleConfigurationMetadataRepository> repositories = new ArrayList<>();

	private ConfigurationMetadataRepositoryJsonBuilder(Charset defaultCharset) {
		this.defaultCharset = defaultCharset;
	}

	/**
	 * Add the content of a {@link ConfigurationMetadataRepository} defined by the
	 * specified {@link InputStream} json document using the default charset. If this
	 * metadata repository holds items that were loaded previously, these are ignored.
	 * <p>
	 * Leaves the stream open when done.
	 * @param inputStream the source input stream
	 * @return this builder
	 * @throws IOException in case of I/O errors
	 */
	public ConfigurationMetadataRepositoryJsonBuilder withJsonResource(InputStream inputStream) throws IOException {
		return withJsonResource(inputStream, this.defaultCharset);
	}

	/**
	 * Add the content of a {@link ConfigurationMetadataRepository} defined by the
	 * specified {@link InputStream} json document using the specified {@link Charset}. If
	 * this metadata repository holds items that were loaded previously, these are
	 * ignored.
	 * <p>
	 * Leaves the stream open when done.
	 * @param inputStream the source input stream
	 * @param charset the charset of the input
	 * @return this builder
	 * @throws IOException in case of I/O errors
	 */
	public ConfigurationMetadataRepositoryJsonBuilder withJsonResource(InputStream inputStream, Charset charset)
			throws IOException {
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream must not be null.");
		}
		this.repositories.add(add(inputStream, charset));
		return this;
	}

	/**
	 * Build a {@link ConfigurationMetadataRepository} with the current state of this
	 * builder.
	 * @return this builder
	 */
	public ConfigurationMetadataRepository build() {
		SimpleConfigurationMetadataRepository result = new SimpleConfigurationMetadataRepository();
		for (SimpleConfigurationMetadataRepository repository : this.repositories) {
			result.include(repository);
		}
		return result;
	}

	private SimpleConfigurationMetadataRepository add(InputStream in, Charset charset) throws IOException {
		try {
			RawConfigurationMetadata metadata = this.reader.read(in, charset);
			return create(metadata);
		}
		catch (Exception ex) {
			throw new IllegalStateException("Failed to read configuration metadata", ex);
		}
	}

	private SimpleConfigurationMetadataRepository create(RawConfigurationMetadata metadata) {
		SimpleConfigurationMetadataRepository repository = new SimpleConfigurationMetadataRepository();
		repository.add(metadata.getSources());
		for (ConfigurationMetadataItem item : metadata.getItems()) {
			ConfigurationMetadataSource source = metadata.getSource(item);
			repository.add(item, source);
		}
		Map<String, ConfigurationMetadataProperty> allProperties = repository.getAllProperties();
		for (ConfigurationMetadataHint hint : metadata.getHints()) {
			ConfigurationMetadataProperty property = allProperties.get(hint.getId());
			if (property != null) {
				addValueHints(property, hint);
			}
			else {
				String id = hint.resolveId();
				property = allProperties.get(id);
				if (property != null) {
					if (hint.isMapKeyHints()) {
						addMapHints(property, hint);
					}
					else {
						addValueHints(property, hint);
					}
				}
			}
		}
		return repository;
	}

	private void addValueHints(ConfigurationMetadataProperty property, ConfigurationMetadataHint hint) {
		property.getHints().getValueHints().addAll(hint.getValueHints());
		property.getHints().getValueProviders().addAll(hint.getValueProviders());
	}

	private void addMapHints(ConfigurationMetadataProperty property, ConfigurationMetadataHint hint) {
		property.getHints().getKeyHints().addAll(hint.getValueHints());
		property.getHints().getKeyProviders().addAll(hint.getValueProviders());
	}

	/**
	 * Create a new builder instance using {@link StandardCharsets#UTF_8} as the default
	 * charset and the specified json resource.
	 * @param inputStreams the source input streams
	 * @return a new {@link ConfigurationMetadataRepositoryJsonBuilder} instance.
	 * @throws IOException on error
	 */
	public static ConfigurationMetadataRepositoryJsonBuilder create(InputStream... inputStreams) throws IOException {
		ConfigurationMetadataRepositoryJsonBuilder builder = create();
		for (InputStream inputStream : inputStreams) {
			builder = builder.withJsonResource(inputStream);
		}
		return builder;
	}

	/**
	 * Create a new builder instance using {@link StandardCharsets#UTF_8} as the default
	 * charset.
	 * @return a new {@link ConfigurationMetadataRepositoryJsonBuilder} instance.
	 */
	public static ConfigurationMetadataRepositoryJsonBuilder create() {
		return create(StandardCharsets.UTF_8);
	}

	/**
	 * Create a new builder instance using the specified default {@link Charset}.
	 * @param defaultCharset the default charset to use
	 * @return a new {@link ConfigurationMetadataRepositoryJsonBuilder} instance.
	 */
	public static ConfigurationMetadataRepositoryJsonBuilder create(Charset defaultCharset) {
		return new ConfigurationMetadataRepositoryJsonBuilder(defaultCharset);
	}

}
