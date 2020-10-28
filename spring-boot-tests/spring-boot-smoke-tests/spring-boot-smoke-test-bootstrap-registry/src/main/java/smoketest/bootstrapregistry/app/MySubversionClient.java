package smoketest.bootstrapregistry.app;

import smoketest.bootstrapregistry.external.svn.SubversionClient;
import smoketest.bootstrapregistry.external.svn.SubversionServerCertificate;

public class MySubversionClient extends SubversionClient {

	public MySubversionClient(SubversionServerCertificate serverCertificate) {
		super(serverCertificate);
	}

	@Override
	public String load(String location) {
		return "my-" + super.load(location);
	}

}
