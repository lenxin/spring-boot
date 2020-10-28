package smoketest.bootstrapregistry.external.svn;

import org.springframework.boot.context.config.ConfigDataResource;

/**
 * A subversion {@link ConfigDataResource}.
 *

 */
class SubversionConfigDataResource extends ConfigDataResource {

	private final String location;

	private final SubversionServerCertificate serverCertificate;

	SubversionConfigDataResource(String location, String serverCertificate) {
		this.location = location;
		this.serverCertificate = SubversionServerCertificate.of(serverCertificate);
	}

	String getLocation() {
		return this.location;
	}

	SubversionServerCertificate getServerCertificate() {
		return this.serverCertificate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SubversionConfigDataResource other = (SubversionConfigDataResource) obj;
		return this.location.equals(other.location);
	}

	@Override
	public int hashCode() {
		return this.location.hashCode();
	}

	@Override
	public String toString() {
		return this.location;
	}

}
