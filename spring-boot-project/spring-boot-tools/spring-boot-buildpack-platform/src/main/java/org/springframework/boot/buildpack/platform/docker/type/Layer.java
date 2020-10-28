package org.springframework.boot.buildpack.platform.docker.type;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.buildpack.platform.io.Content;
import org.springframework.boot.buildpack.platform.io.IOConsumer;
import org.springframework.boot.buildpack.platform.io.InspectedContent;
import org.springframework.boot.buildpack.platform.io.Layout;
import org.springframework.boot.buildpack.platform.io.TarArchive;
import org.springframework.util.Assert;

/**
 * A layer that can be written to an {@link ImageArchive}.
 *

 * @since 2.3.0
 */
public class Layer implements Content {

	private final Content content;

	private final LayerId id;

	Layer(TarArchive tarArchive) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.content = InspectedContent.of(tarArchive::writeTo, digest::update);
		this.id = LayerId.ofSha256Digest(digest.digest());
	}

	/**
	 * Return the ID of the layer.
	 * @return the layer ID
	 */
	public LayerId getId() {
		return this.id;
	}

	@Override
	public int size() {
		return this.content.size();
	}

	@Override
	public void writeTo(OutputStream outputStream) throws IOException {
		this.content.writeTo(outputStream);
	}

	/**
	 * Factory method to create a new {@link Layer} with a specific {@link Layout}.
	 * @param layout the layer layout
	 * @return a new layer instance
	 * @throws IOException on IO error
	 */
	public static Layer of(IOConsumer<Layout> layout) throws IOException {
		Assert.notNull(layout, "Layout must not be null");
		return fromTarArchive(TarArchive.of(layout));
	}

	/**
	 * Factory method to create a new {@link Layer} from a {@link TarArchive}.
	 * @param tarArchive the contents of the layer
	 * @return a new layer instance
	 * @throws IOException on error
	 */
	public static Layer fromTarArchive(TarArchive tarArchive) throws IOException {
		Assert.notNull(tarArchive, "TarArchive must not be null");
		try {
			return new Layer(tarArchive);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException(ex);
		}
	}

}
