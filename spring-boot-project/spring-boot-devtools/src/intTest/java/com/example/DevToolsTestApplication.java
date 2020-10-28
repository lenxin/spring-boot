package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.context.WebServerPortFileWriter;

@SpringBootApplication
public class DevToolsTestApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DevToolsTestApplication.class).listeners(new WebServerPortFileWriter(args[0]))
				.run(args);
	}

}
