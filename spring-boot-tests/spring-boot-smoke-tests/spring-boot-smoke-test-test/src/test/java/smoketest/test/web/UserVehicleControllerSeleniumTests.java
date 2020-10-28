package smoketest.test.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import smoketest.test.service.VehicleDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Selenium based tests for {@link UserVehicleController}.
 *

 */
@WebMvcTest(UserVehicleController.class)
class UserVehicleControllerSeleniumTests {

	@Autowired
	private WebDriver webDriver;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	void getVehicleWhenRequestingTextShouldReturnMakeAndModel() {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		this.webDriver.get("/sboot/vehicle.html");
		WebElement element = this.webDriver.findElement(By.tagName("h1"));
		assertThat(element.getText()).isEqualTo("Honda Civic");
	}

}
