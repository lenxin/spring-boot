package io.spring.concourse.releasescripts.command;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * {@link ApplicationRunner} to delegate incoming requests to commands.
 *

 */
@Component
public class CommandProcessor implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);

	private final List<Command> commands;

	public CommandProcessor(List<Command> commands) {
		this.commands = Collections.unmodifiableList(commands);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("Running command processor");
		List<String> nonOptionArgs = args.getNonOptionArgs();
		Assert.state(!nonOptionArgs.isEmpty(), "No command argument specified");
		String request = nonOptionArgs.get(0);
		Command command = this.commands.stream().filter((candidate) -> candidate.getName().equals(request)).findFirst()
				.orElseThrow(() -> new IllegalStateException("Unknown command '" + request + "'"));
		logger.debug("Found command " + command.getClass().getName());
		command.run(args);
	}

}
