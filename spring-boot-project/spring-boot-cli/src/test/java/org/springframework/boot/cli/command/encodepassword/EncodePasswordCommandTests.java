package org.springframework.boot.cli.command.encodepassword;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.cli.command.status.ExitStatus;
import org.springframework.boot.cli.util.MockLog;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link EncodePasswordCommand}.
 *

 */
@ExtendWith(MockitoExtension.class)
class EncodePasswordCommandTests {

	private MockLog log;

	@Captor
	private ArgumentCaptor<String> message;

	@BeforeEach
	void setup() {
		this.log = MockLog.attach();
	}

	@AfterEach
	void cleanup() {
		MockLog.clear();
	}

	@Test
	void encodeWithNoAlgorithmShouldUseBcrypt() throws Exception {
		EncodePasswordCommand command = new EncodePasswordCommand();
		ExitStatus status = command.run("boot");
		verify(this.log).info(this.message.capture());
		assertThat(this.message.getValue()).startsWith("{bcrypt}");
		assertThat(PasswordEncoderFactories.createDelegatingPasswordEncoder().matches("boot", this.message.getValue()))
				.isTrue();
		assertThat(status).isEqualTo(ExitStatus.OK);
	}

	@Test
	void encodeWithBCryptShouldUseBCrypt() throws Exception {
		EncodePasswordCommand command = new EncodePasswordCommand();
		ExitStatus status = command.run("-a", "bcrypt", "boot");
		verify(this.log).info(this.message.capture());
		assertThat(this.message.getValue()).doesNotStartWith("{");
		assertThat(new BCryptPasswordEncoder().matches("boot", this.message.getValue())).isTrue();
		assertThat(status).isEqualTo(ExitStatus.OK);
	}

	@Test
	void encodeWithPbkdf2ShouldUsePbkdf2() throws Exception {
		EncodePasswordCommand command = new EncodePasswordCommand();
		ExitStatus status = command.run("-a", "pbkdf2", "boot");
		verify(this.log).info(this.message.capture());
		assertThat(this.message.getValue()).doesNotStartWith("{");
		assertThat(new Pbkdf2PasswordEncoder().matches("boot", this.message.getValue())).isTrue();
		assertThat(status).isEqualTo(ExitStatus.OK);
	}

	@Test
	void encodeWithUnknownAlgorithmShouldExitWithError() throws Exception {
		EncodePasswordCommand command = new EncodePasswordCommand();
		ExitStatus status = command.run("--algorithm", "bad", "boot");
		verify(this.log).error("Unknown algorithm, valid options are: default,bcrypt,pbkdf2");
		assertThat(status).isEqualTo(ExitStatus.ERROR);
	}

}
