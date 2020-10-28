package smoketest.bootstrapregistry.external.svn;

import org.springframework.util.StringUtils;

/**
 * A certificate that can be used to provide a secure connection to the subversion server.
 *

 */
public class SubversionServerCertificate {

	private final String data;

	SubversionServerCertificate(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return this.data;
	}

	public static SubversionServerCertificate of(String data) {
		return StringUtils.hasText(data) ? new SubversionServerCertificate(data) : null;
	}

}
