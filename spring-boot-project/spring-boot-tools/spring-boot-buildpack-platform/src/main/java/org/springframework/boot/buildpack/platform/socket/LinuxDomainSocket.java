package org.springframework.boot.buildpack.platform.socket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;

import org.springframework.util.Assert;

/**
 * {@link DomainSocket} implementation for Linux based platforms.
 *

 */
class LinuxDomainSocket extends DomainSocket {

	static {
		Native.register(Platform.C_LIBRARY_NAME);
	}

	LinuxDomainSocket(String path) throws IOException {
		super(path);
	}

	private static final int MAX_PATH_LENGTH = 108;

	@Override
	protected void connect(String path, int handle) {
		SockaddrUn address = new SockaddrUn(AF_LOCAL, path.getBytes(StandardCharsets.UTF_8));
		connect(handle, address, address.size());
	}

	private native int connect(int fd, SockaddrUn address, int addressLen) throws LastErrorException;

	/**
	 * Native {@code sockaddr_un} structure as defined in {@code sys/un.h}.
	 */
	public static class SockaddrUn extends Structure implements Structure.ByReference {

		public short sunFamily;

		public byte[] sunPath = new byte[MAX_PATH_LENGTH];

		private SockaddrUn(byte sunFamily, byte[] path) {
			Assert.isTrue(path.length < MAX_PATH_LENGTH, () -> "Path cannot exceed " + MAX_PATH_LENGTH + " bytes");
			System.arraycopy(path, 0, this.sunPath, 0, path.length);
			this.sunPath[path.length] = 0;
			this.sunFamily = sunFamily;
			allocateMemory();
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { "sunFamily", "sunPath" });
		}

	}

}
