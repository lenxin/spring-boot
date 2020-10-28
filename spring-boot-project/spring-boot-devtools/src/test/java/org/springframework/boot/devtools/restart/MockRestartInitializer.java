package org.springframework.boot.devtools.restart;

import java.net.URL;

/**
 * Simple mock {@link RestartInitializer} that returns an empty array of URLs.
 *

 */
public class MockRestartInitializer implements RestartInitializer {

	@Override
	public URL[] getInitialUrls(Thread thread) {
		return new URL[] {};
	}

}
