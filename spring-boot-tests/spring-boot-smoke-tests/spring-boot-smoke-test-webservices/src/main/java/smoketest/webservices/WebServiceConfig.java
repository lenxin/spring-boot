package smoketest.webservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@Configuration(proxyBeanMethods = false)
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean(name = "holiday")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
		wsdl.setPortTypeName("HumanResource");
		wsdl.setLocationUri("/holidayService/");
		wsdl.setTargetNamespace("https://company.example.com/hr/definitions");
		wsdl.setSchema(countriesSchema);
		return wsdl;
	}

}
