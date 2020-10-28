package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController @RestClientTest} used by
 * {@link WebMvcTestHateoasIntegrationTests}.
 *

 */
@RestController
@RequestMapping("/hateoas")
class HateoasController {

	@RequestMapping("/resource")
	EntityModel<Map<String, String>> resource() {
		return EntityModel.of(new HashMap<>(), Link.of("self", LinkRelation.of("https://api.example.com")));
	}

	@RequestMapping("/plain")
	Map<String, String> plain() {
		return new HashMap<>();
	}

}
