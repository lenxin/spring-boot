package org.springframework.boot.devtools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.config.AnsiOutputApplicationListener;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.logging.ClasspathLoggingApplicationListener;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.boot.devtools.remote.client.RemoteClientConfiguration;
import org.springframework.boot.devtools.restart.RestartInitializer;
import org.springframework.boot.devtools.restart.RestartScopeInitializer;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessorsFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;

/**
 * Application that can be used to establish a link to remotely running Spring Boot code.
 * Allows remote updates (if enabled). This class should be launched from within your IDE
 * and should have the same classpath configuration as the locally developed application.
 * The remote URL of the application should be provided as a non-option argument.
 *

 * @since 1.3.0
 * @see RemoteClientConfiguration
 */
public final class RemoteSpringApplication {

	private RemoteSpringApplication() {
	}

	private void run(String[] args) {
		Restarter.initialize(args, RestartInitializer.NONE);
		SpringApplication application = new SpringApplication(RemoteClientConfiguration.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.setBanner(getBanner());
		application.setInitializers(getInitializers());
		application.setListeners(getListeners());
		application.run(args);
		waitIndefinitely();
	}

	private Collection<ApplicationContextInitializer<?>> getInitializers() {
		List<ApplicationContextInitializer<?>> initializers = new ArrayList<>();
		initializers.add(new RestartScopeInitializer());
		return initializers;
	}

	private Collection<ApplicationListener<?>> getListeners() {
		List<ApplicationListener<?>> listeners = new ArrayList<>();
		listeners.add(new AnsiOutputApplicationListener());
		listeners.add(new EnvironmentPostProcessorApplicationListener(
				EnvironmentPostProcessorsFactory.of(ConfigDataEnvironmentPostProcessor.class)));
		listeners.add(new ClasspathLoggingApplicationListener());
		listeners.add(new LoggingApplicationListener());
		listeners.add(new RemoteUrlPropertyExtractor());
		return listeners;
	}

	private Banner getBanner() {
		ClassPathResource banner = new ClassPathResource("remote-banner.txt", RemoteSpringApplication.class);
		return new ResourceBanner(banner);
	}

	private void waitIndefinitely() {
		while (true) {
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Run the {@link RemoteSpringApplication}.
	 * @param args the program arguments (including the remote URL as a non-option
	 * argument)
	 */
	public static void main(String[] args) {
		new RemoteSpringApplication().run(args);
	}

}
