package io.spring.concourse.releasescripts.command;

import org.springframework.boot.ApplicationArguments;
import org.springframework.util.ClassUtils;

/**

 */
public interface Command {

	default String getName() {
		String name = ClassUtils.getShortName(getClass());
		int lastDot = name.lastIndexOf(".");
		if (lastDot != -1) {
			name = name.substring(lastDot + 1, name.length());
		}
		if (name.endsWith("Command")) {
			name = name.substring(0, name.length() - "Command".length());
		}
		return name.toLowerCase();
	}

	void run(ApplicationArguments args) throws Exception;

}
