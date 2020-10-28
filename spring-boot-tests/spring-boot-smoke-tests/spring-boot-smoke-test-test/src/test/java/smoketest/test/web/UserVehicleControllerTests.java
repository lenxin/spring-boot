package smoketest.test.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import smoketest.test.WelcomeCommandLineRunner;
import smoketest.test.domain.VehicleIdentificationNumber;
import smoketest.test.service.VehicleDetails;
import smoketest.test.service.VehicleIdentificationNumberNotFoundException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @WebMvcTest} based tests for {@link UserVehicleController}.
 *

 */
@WebMvcTest(UserVehicleController.class)
class UserVehicleControllerTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ApplicationContext applicationContext;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(content().string("Honda Civic"));
	}

	@Test
	void getVehicleWhenRequestingJsonShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/sboot/vehicle").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{'make':'Honda','model':'Civic'}"));
	}

	@Test
	void getVehicleWhenRequestingHtmlShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/sboot/vehicle.html").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1>Honda Civic</h1>")));
	}

	@Test
	void getVehicleWhenUserNotFoundShouldReturnNotFound() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot")).willThrow(new UserNameNotFoundException("sboot"));
		this.mvc.perform(get("/sboot/vehicle")).andExpect(status().isNotFound());
	}

	@Test
	void getVehicleWhenVinNotFoundShouldReturnNotFound() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot"))
				.willThrow(new VehicleIdentificationNumberNotFoundException(VIN));
		this.mvc.perform(get("/sboot/vehicle")).andExpect(status().isNotFound());
	}

	@Test
	void welcomeCommandLineRunnerShouldNotBeAvailable() {
		// Since we're a @WebMvcTest WelcomeCommandLineRunner should not be available.
		Assertions.assertThatThrownBy(() -> this.applicationContext.getBean(WelcomeCommandLineRunner.class))
				.isInstanceOf(NoSuchBeanDefinitionException.class);
	}

}
