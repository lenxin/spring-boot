package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerOne {

	@RequestMapping("/one")
	public String one() {
		return "one";
	}

}
