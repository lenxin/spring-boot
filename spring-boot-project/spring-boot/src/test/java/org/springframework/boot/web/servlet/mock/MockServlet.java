package org.springframework.boot.web.servlet.mock;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Simple mock Servlet that does nothing.
 *

 */
@SuppressWarnings("serial")
public class MockServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	}

}
