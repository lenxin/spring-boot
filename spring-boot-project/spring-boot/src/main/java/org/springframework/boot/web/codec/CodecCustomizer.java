package org.springframework.boot.web.codec;

import org.springframework.http.codec.CodecConfigurer;

/**
 * Callback interface that can be used to customize codecs configuration for an HTTP
 * client and/or server with a {@link CodecConfigurer}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface CodecCustomizer {

	/**
	 * Callback to customize a {@link CodecConfigurer} instance.
	 * @param configurer codec configurer to customize
	 */
	void customize(CodecConfigurer configurer);

}
