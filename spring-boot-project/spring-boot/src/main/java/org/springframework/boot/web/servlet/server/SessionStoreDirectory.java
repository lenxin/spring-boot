package org.springframework.boot.web.servlet.server;

import java.io.File;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.util.Assert;

/**
 * Manages a session store directory.
 *

 * @see AbstractServletWebServerFactory
 */
class SessionStoreDirectory {

	private File directory;

	File getDirectory() {
		return this.directory;
	}

	void setDirectory(File directory) {
		this.directory = directory;
	}

	File getValidDirectory(boolean mkdirs) {
		File dir = getDirectory();
		if (dir == null) {
			return new ApplicationTemp().getDir("servlet-sessions");
		}
		if (!dir.isAbsolute()) {
			dir = new File(new ApplicationHome().getDir(), dir.getPath());
		}
		if (!dir.exists() && mkdirs) {
			dir.mkdirs();
		}
		assertDirectory(mkdirs, dir);
		return dir;
	}

	private void assertDirectory(boolean mkdirs, File dir) {
		Assert.state(!mkdirs || dir.exists(), () -> "Session dir " + dir + " does not exist");
		Assert.state(!dir.isFile(), () -> "Session dir " + dir + " points to a file");
	}

}
