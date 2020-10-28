package org.springframework.boot.buildpack.platform.docker.transport;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.boot.buildpack.platform.docker.configuration.DockerConfiguration;
import org.springframework.boot.buildpack.platform.docker.configuration.DockerHost;
import org.springframework.boot.buildpack.platform.io.IOConsumer;
import org.springframework.boot.buildpack.platform.system.Environment;

/**
 * HTTP transport used for docker access.
 *


 * @since 2.3.0
 */
public interface HttpTransport {

	/**
	 * Perform a HTTP GET operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response get(URI uri) throws IOException;

	/**
	 * Perform a HTTP POST operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response post(URI uri) throws IOException;

	/**
	 * Perform a HTTP POST operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @param registryAuth registry authentication credentials
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response post(URI uri, String registryAuth) throws IOException;

	/**
	 * Perform a HTTP POST operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @param contentType the content type to write
	 * @param writer a content writer
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response post(URI uri, String contentType, IOConsumer<OutputStream> writer) throws IOException;

	/**
	 * Perform a HTTP PUT operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @param contentType the content type to write
	 * @param writer a content writer
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response put(URI uri, String contentType, IOConsumer<OutputStream> writer) throws IOException;

	/**
	 * Perform a HTTP DELETE operation.
	 * @param uri the destination URI (excluding any host/port)
	 * @return the operation response
	 * @throws IOException on IO error
	 */
	Response delete(URI uri) throws IOException;

	/**
	 * Create the most suitable {@link HttpTransport} based on the
	 * {@link Environment#SYSTEM system environment}.
	 * @return a {@link HttpTransport} instance
	 */
	static HttpTransport create() {
		return create(Environment.SYSTEM);
	}

	/**
	 * Create the most suitable {@link HttpTransport} based on the
	 * {@link Environment#SYSTEM system environment}.
	 * @param dockerHost the Docker engine host configuration
	 * @return a {@link HttpTransport} instance
	 */
	static HttpTransport create(DockerHost dockerHost) {
		return create(Environment.SYSTEM, dockerHost);
	}

	/**
	 * Create the most suitable {@link HttpTransport} based on the given
	 * {@link Environment}.
	 * @param environment the source environment
	 * @return a {@link HttpTransport} instance
	 */
	static HttpTransport create(Environment environment) {
		return create(environment, null);
	}

	/**
	 * Create the most suitable {@link HttpTransport} based on the given
	 * {@link Environment} and {@link DockerConfiguration}.
	 * @param environment the source environment
	 * @param dockerHost the Docker engine host configuration
	 * @return a {@link HttpTransport} instance
	 */
	static HttpTransport create(Environment environment, DockerHost dockerHost) {
		HttpTransport remote = RemoteHttpClientTransport.createIfPossible(environment, dockerHost);
		return (remote != null) ? remote : LocalHttpClientTransport.create(environment);
	}

	/**
	 * An HTTP operation response.
	 */
	interface Response extends Closeable {

		/**
		 * Return the content of the response.
		 * @return the response content
		 * @throws IOException on IO error
		 */
		InputStream getContent() throws IOException;

	}

}
