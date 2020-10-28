package org.springframework.boot.actuate.amqp;

import java.util.Collections;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RabbitHealthIndicator}.
 *

 */
@ExtendWith(MockitoExtension.class)
class RabbitHealthIndicatorTests {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private Channel channel;

	@Test
	void createWhenRabbitTemplateIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RabbitHealthIndicator(null))
				.withMessageContaining("RabbitTemplate must not be null");
	}

	@Test
	void healthWhenConnectionSucceedsShouldReturnUpWithVersion() {
		givenTemplateExecutionWillInvokeCallback();
		Connection connection = mock(Connection.class);
		given(this.channel.getConnection()).willReturn(connection);
		given(connection.getServerProperties()).willReturn(Collections.singletonMap("version", "123"));
		Health health = new RabbitHealthIndicator(this.rabbitTemplate).health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).containsEntry("version", "123");
	}

	@Test
	void healthWhenConnectionFailsShouldReturnDown() {
		givenTemplateExecutionWillInvokeCallback();
		given(this.channel.getConnection()).willThrow(new RuntimeException());
		Health health = new RabbitHealthIndicator(this.rabbitTemplate).health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
	}

	private void givenTemplateExecutionWillInvokeCallback() {
		given(this.rabbitTemplate.execute(any())).willAnswer((invocation) -> {
			ChannelCallback<?> callback = invocation.getArgument(0);
			return callback.doInRabbit(this.channel);
		});
	}

}
