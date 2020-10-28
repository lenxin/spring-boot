package smoketest.bootstrapregistry.external.svn;

/**
 * A client that can connect to a subversion server.
 *

 */
public class SubversionClient {

	private SubversionServerCertificate serverCertificate;

	public SubversionClient(SubversionServerCertificate serverCertificate) {
		this.serverCertificate = serverCertificate;
	}

	public String load(String location) {
		return "data from svn / " + location + "[" + this.serverCertificate + "]";
	}

}
