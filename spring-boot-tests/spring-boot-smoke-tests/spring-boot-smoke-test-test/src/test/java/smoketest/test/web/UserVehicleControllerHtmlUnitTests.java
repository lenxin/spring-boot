package smoketest.test.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import smoketest.test.service.VehicleDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * HtmlUnit based tests for {@link UserVehicleController}.
 *

 */
@WebMvcTest(UserVehicleController.class)
class UserVehicleControllerHtmlUnitTests {

	@Autowired
	private WebClient webClient;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		HtmlPage page = this.webClient.getPage("/sboot/vehicle.html");
		assertThat(page.getBody().getTextContent()).isEqualTo("Honda Civic");
	}

}
