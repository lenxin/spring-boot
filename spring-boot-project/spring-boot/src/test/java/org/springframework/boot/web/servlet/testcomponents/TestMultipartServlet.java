package org.springframework.boot.web.servlet.testcomponents;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/test-multipart")
@MultipartConfig(location = "test", maxFileSize = 1024, maxRequestSize = 2048, fileSizeThreshold = 512)
public class TestMultipartServlet extends HttpServlet {

}
