package org.springframework.boot.test.web.htmlunit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * {@link WebClient} will automatically prefix relative URLs with
 * <code>localhost:$&#123;local.server.port&#125;</code>.
 *

 * @since 1.4.0
 */
public class LocalHostWebClient extends WebClient {

	private final Environment environment;

	public LocalHostWebClient(Environment environment) {
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	@Override
	public <P extends Page> P getPage(String url) throws IOException, FailingHttpStatusCodeException {
		if (url.startsWith("/")) {
			String port = this.environment.getProperty("local.server.port", "8080");
			url = "http://localhost:" + port + url;
		}
		return super.getPage(url);
	}

}
