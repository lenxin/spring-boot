package smoketest.integration.producer;

import java.io.File;
import java.io.FileOutputStream;

import smoketest.integration.ServiceProperties;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServiceProperties.class)
public class ProducerApplication implements ApplicationRunner {

	private final ServiceProperties serviceProperties;

	public ProducerApplication(ServiceProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.serviceProperties.getInputDir().mkdirs();
		if (!args.getNonOptionArgs().isEmpty()) {
			FileOutputStream stream = new FileOutputStream(
					new File(this.serviceProperties.getInputDir(), "data" + System.currentTimeMillis() + ".txt"));
			for (String arg : args.getNonOptionArgs()) {
				stream.write(arg.getBytes());
			}
			stream.flush();
			stream.close();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}
