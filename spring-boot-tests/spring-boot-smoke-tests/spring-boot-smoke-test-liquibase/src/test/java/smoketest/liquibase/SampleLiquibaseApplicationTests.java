package smoketest.liquibase;

import java.net.ConnectException;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.core.NestedCheckedException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class SampleLiquibaseApplicationTests {

	private Locale defaultLocale;

	@BeforeEach
	void init() throws SecurityException {
		this.defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.ENGLISH);
	}

	@AfterEach
	void restoreLocale() {
		Locale.setDefault(this.defaultLocale);
	}

	@Test
	void testDefaultSettings(CapturedOutput output) throws Exception {
		try {
			SampleLiquibaseApplication.main(new String[] { "--server.port=0" });
		}
		catch (IllegalStateException ex) {
			if (serverNotRunning(ex)) {
				return;
			}
		}
		assertThat(output).contains("Successfully acquired change log lock")
				.contains("Creating database history table with name: PUBLIC.DATABASECHANGELOG")
				.contains("Table person created")
				.contains("ChangeSet classpath:/db/changelog/db.changelog-master.yaml::1::"
						+ "marceloverdijk ran successfully")
				.contains("New row inserted into person")
				.contains("ChangeSet classpath:/db/changelog/"
						+ "db.changelog-master.yaml::2::marceloverdijk ran successfully")
				.contains("Successfully released change log lock");
	}

	@SuppressWarnings("serial")
	private boolean serverNotRunning(IllegalStateException ex) {
		NestedCheckedException nested = new NestedCheckedException("failed", ex) {
		};
		if (nested.contains(ConnectException.class)) {
			Throwable root = nested.getRootCause();
			if (root.getMessage().contains("Connection refused")) {
				return true;
			}
		}
		return false;
	}

}
