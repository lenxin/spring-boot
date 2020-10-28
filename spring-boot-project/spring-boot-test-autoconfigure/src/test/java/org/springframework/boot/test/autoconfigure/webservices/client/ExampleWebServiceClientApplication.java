package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} used with
 * {@link WebServiceClientTest @WebServiceClientTest} tests.
 *

 */
@SpringBootApplication
@Import(WebServiceMarshallerConfiguration.class)
public class ExampleWebServiceClientApplication {

}
