package smoketest.jndi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	private final AccountService service;

	private final AccountRepository repository;

	public WebController(AccountService service, AccountRepository repository) {
		this.service = service;
		this.repository = repository;
	}

	@GetMapping("/")
	public String hello() {
		System.out.println("Count is " + this.repository.count());
		this.service.createAccountAndNotify("josh");
		try {
			this.service.createAccountAndNotify("error");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		long count = this.repository.count();
		System.out.println("Count is " + count);
		return "Count is " + count;
	}

}
