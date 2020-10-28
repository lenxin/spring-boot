package org.springframework.boot.actuate.metrics.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Outcome}.
 *

 */
public class OutcomeTests {

	@Test
	void outcomeForInformationalStatusIsInformational() {
		for (int status = 100; status < 200; status++) {
			assertThat(Outcome.forStatus(status)).isEqualTo(Outcome.INFORMATIONAL);
		}
	}

	@Test
	void outcomeForSuccessStatusIsSuccess() {
		for (int status = 200; status < 300; status++) {
			assertThat(Outcome.forStatus(status)).isEqualTo(Outcome.SUCCESS);
		}
	}

	@Test
	void outcomeForRedirectionStatusIsRedirection() {
		for (int status = 300; status < 400; status++) {
			assertThat(Outcome.forStatus(status)).isEqualTo(Outcome.REDIRECTION);
		}
	}

	@Test
	void outcomeForClientErrorStatusIsClientError() {
		for (int status = 400; status < 500; status++) {
			assertThat(Outcome.forStatus(status)).isEqualTo(Outcome.CLIENT_ERROR);
		}
	}

	@Test
	void outcomeForServerErrorStatusIsServerError() {
		for (int status = 500; status < 600; status++) {
			assertThat(Outcome.forStatus(status)).isEqualTo(Outcome.SERVER_ERROR);
		}
	}

	@Test
	void outcomeForStatusBelowLowestKnownSeriesIsUnknown() {
		assertThat(Outcome.forStatus(99)).isEqualTo(Outcome.UNKNOWN);
	}

	@Test
	void outcomeForStatusAboveHighestKnownSeriesIsUnknown() {
		assertThat(Outcome.forStatus(600)).isEqualTo(Outcome.UNKNOWN);
	}

}
