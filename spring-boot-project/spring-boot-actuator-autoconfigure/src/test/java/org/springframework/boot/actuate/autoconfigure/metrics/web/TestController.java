package org.springframework.boot.actuate.autoconfigure.metrics.web;

import io.micrometer.core.annotation.Timed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller used by metrics tests.
 *



 */
@RestController
public class TestController {

	@GetMapping("test0")
	public String test0() {
		return "test0";
	}

	@GetMapping("test1")
	public String test1() {
		return "test1";
	}

	@GetMapping("test2")
	public String test2() {
		return "test2";
	}

	@Timed
	@GetMapping("test3")
	public String test3() {
		return "test3";
	}

}
