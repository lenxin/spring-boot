package org.springframework.boot.context.embedded;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.testsupport.BuildOutput;

/**
 * {@link AbstractApplicationLauncher} that launches a packaged Spring Boot application
 * using {@code java -jar}.
 *

 */
class PackagedApplicationLauncher extends AbstractApplicationLauncher {

	PackagedApplicationLauncher(ApplicationBuilder applicationBuilder, BuildOutput buildOutput) {
		super(applicationBuilder, buildOutput);
	}

	@Override
	protected File getWorkingDirectory() {
		return null;
	}

	@Override
	protected String getDescription(String packaging) {
		return "packaged " + packaging;
	}

	@Override
	protected List<String> getArguments(File archive, File serverPortFile) {
		return Arrays.asList("-jar", archive.getAbsolutePath(), serverPortFile.getAbsolutePath());
	}

}
