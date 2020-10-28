package org.springframework.boot.devtools.tunnel.payload;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Utility class that forwards {@link HttpTunnelPayload} instances to a destination
 * channel, respecting sequence order.
 *

 * @since 1.3.0
 */
public class HttpTunnelPayloadForwarder {

	private static final int MAXIMUM_QUEUE_SIZE = 100;

	private final Map<Long, HttpTunnelPayload> queue = new HashMap<>();

	private final Object monitor = new Object();

	private final WritableByteChannel targetChannel;

	private long lastRequestSeq = 0;

	/**
	 * Create a new {@link HttpTunnelPayloadForwarder} instance.
	 * @param targetChannel the target channel
	 */
	public HttpTunnelPayloadForwarder(WritableByteChannel targetChannel) {
		Assert.notNull(targetChannel, "TargetChannel must not be null");
		this.targetChannel = targetChannel;
	}

	public void forward(HttpTunnelPayload payload) throws IOException {
		synchronized (this.monitor) {
			long seq = payload.getSequence();
			if (this.lastRequestSeq != seq - 1) {
				Assert.state(this.queue.size() < MAXIMUM_QUEUE_SIZE, "Too many messages queued");
				this.queue.put(seq, payload);
				return;
			}
			payload.logOutgoing();
			payload.writeTo(this.targetChannel);
			this.lastRequestSeq = seq;
			HttpTunnelPayload queuedItem = this.queue.get(seq + 1);
			if (queuedItem != null) {
				forward(queuedItem);
			}
		}
	}

}
