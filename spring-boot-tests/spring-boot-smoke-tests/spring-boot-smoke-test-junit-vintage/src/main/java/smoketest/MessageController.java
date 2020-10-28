package smoketest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@GetMapping("/hi")
	public String hello() {
		return "Hello World";
	}

}
