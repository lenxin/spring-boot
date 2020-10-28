package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import javax.validation.constraints.Size;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example {@link Controller @Controller} used with {@link WebMvcTest @WebMvcTest} tests.
 *

 */
@RestController
@Validated
public class ExampleController3 {

	@GetMapping("/three/{id}")
	public String three(@PathVariable @Size(max = 4) String id) {
		return "Hello " + id;
	}

}
