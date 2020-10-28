package smoketest.parent;

import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

@SpringBootApplication
public class SampleParentContextApplication {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Parent.class).child(SampleParentContextApplication.class).run(args);
	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	protected static class Parent {

		private final ServiceProperties serviceProperties;

		public Parent(ServiceProperties serviceProperties) {
			this.serviceProperties = serviceProperties;
		}

		@Bean
		public FileReadingMessageSource fileReader() {
			FileReadingMessageSource reader = new FileReadingMessageSource();
			reader.setDirectory(this.serviceProperties.getInputDir());
			return reader;
		}

		@Bean
		public DirectChannel inputChannel() {
			return new DirectChannel();
		}

		@Bean
		public DirectChannel outputChannel() {
			return new DirectChannel();
		}

		@Bean
		public FileWritingMessageHandler fileWriter() {
			FileWritingMessageHandler writer = new FileWritingMessageHandler(this.serviceProperties.getOutputDir());
			writer.setExpectReply(false);
			return writer;
		}

		@Bean
		public IntegrationFlow integrationFlow(SampleEndpoint endpoint) {
			return IntegrationFlows.from(fileReader(), new FixedRatePoller()).channel(inputChannel()).handle(endpoint)
					.channel(outputChannel()).handle(fileWriter()).get();
		}

		private static class FixedRatePoller implements Consumer<SourcePollingChannelAdapterSpec> {

			@Override
			public void accept(SourcePollingChannelAdapterSpec spec) {
				spec.poller(Pollers.fixedRate(500));
			}

		}

	}

}
