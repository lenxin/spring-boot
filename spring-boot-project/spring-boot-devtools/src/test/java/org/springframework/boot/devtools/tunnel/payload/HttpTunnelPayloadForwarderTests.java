package org.springframework.boot.devtools.tunnel.payload;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link HttpTunnelPayloadForwarder}.
 *

 */
class HttpTunnelPayloadForwarderTests {

	@Test
	void targetChannelMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new HttpTunnelPayloadForwarder(null))
				.withMessageContaining("TargetChannel must not be null");
	}

	@Test
	void forwardInSequence() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		WritableByteChannel channel = Channels.newChannel(out);
		HttpTunnelPayloadForwarder forwarder = new HttpTunnelPayloadForwarder(channel);
		forwarder.forward(payload(1, "he"));
		forwarder.forward(payload(2, "ll"));
		forwarder.forward(payload(3, "o"));
		assertThat(out.toByteArray()).isEqualTo("hello".getBytes());
	}

	@Test
	void forwardOutOfSequence() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		WritableByteChannel channel = Channels.newChannel(out);
		HttpTunnelPayloadForwarder forwarder = new HttpTunnelPayloadForwarder(channel);
		forwarder.forward(payload(3, "o"));
		forwarder.forward(payload(2, "ll"));
		forwarder.forward(payload(1, "he"));
		assertThat(out.toByteArray()).isEqualTo("hello".getBytes());
	}

	@Test
	void overflow() throws Exception {
		WritableByteChannel channel = Channels.newChannel(new ByteArrayOutputStream());
		HttpTunnelPayloadForwarder forwarder = new HttpTunnelPayloadForwarder(channel);
		assertThatIllegalStateException().isThrownBy(() -> {
			for (int i = 2; i < 130; i++) {
				forwarder.forward(payload(i, "data" + i));
			}
		}).withMessageContaining("Too many messages queued");
	}

	private HttpTunnelPayload payload(long sequence, String data) {
		return new HttpTunnelPayload(sequence, ByteBuffer.wrap(data.getBytes()));
	}

}
