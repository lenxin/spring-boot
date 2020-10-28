package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;

/**
 * A {@link TomcatContextCustomizer} that disables Tomcat's reflective reference clearing
 * to avoid reflective access warnings on Java 9 and later JVMs.
 *

 */
class DisableReferenceClearingContextCustomizer implements TomcatContextCustomizer {

	@Override
	public void customize(Context context) {
		if (!(context instanceof StandardContext)) {
			return;
		}
		StandardContext standardContext = (StandardContext) context;
		try {
			standardContext.setClearReferencesObjectStreamClassCaches(false);
			standardContext.setClearReferencesRmiTargets(false);
			standardContext.setClearReferencesThreadLocals(false);
		}
		catch (NoSuchMethodError ex) {
			// Earlier version of Tomcat (probably without
			// setClearReferencesThreadLocals). Continue.
		}
	}

}
