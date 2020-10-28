package org.springframework.boot.test.web.htmlunit.webdriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.Capabilities;

import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.util.Assert;

/**
 * {@link LocalHostWebConnectionHtmlUnitDriver} will automatically prefix relative URLs
 * with <code>localhost:$&#123;local.server.port&#125;</code>.
 *

 * @since 1.4.0
 */
public class LocalHostWebConnectionHtmlUnitDriver extends WebConnectionHtmlUnitDriver {

	private final Environment environment;

	public LocalHostWebConnectionHtmlUnitDriver(Environment environment) {
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	public LocalHostWebConnectionHtmlUnitDriver(Environment environment, boolean enableJavascript) {
		super(enableJavascript);
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	public LocalHostWebConnectionHtmlUnitDriver(Environment environment, BrowserVersion browserVersion) {
		super(browserVersion);
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	public LocalHostWebConnectionHtmlUnitDriver(Environment environment, Capabilities capabilities) {
		super(capabilities);
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	@Override
	public void get(String url) {
		if (url.startsWith("/")) {
			String port = this.environment.getProperty("local.server.port", "8080");
			url = "http://localhost:" + port + url;
		}
		super.get(url);
	}

}
