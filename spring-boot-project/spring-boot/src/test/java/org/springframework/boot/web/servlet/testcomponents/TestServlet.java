package org.springframework.boot.web.servlet.testcomponents;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print(((String) req.getServletContext().getAttribute("listenerAttribute")) + " "
				+ req.getAttribute("filterAttribute") + " " + req.getAttribute("listenerAddedFilterAttribute"));
		resp.getWriter().flush();
	}

}
