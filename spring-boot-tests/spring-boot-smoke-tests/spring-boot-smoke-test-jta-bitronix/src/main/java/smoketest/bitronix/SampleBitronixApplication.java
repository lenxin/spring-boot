package smoketest.bitronix;

import java.io.Closeable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SampleBitronixApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(SampleBitronixApplication.class, args);
		AccountService service = context.getBean(AccountService.class);
		AccountRepository repository = context.getBean(AccountRepository.class);
		service.createAccountAndNotify("josh");
		System.out.println("Count is " + repository.count());
		try {
			service.createAccountAndNotify("error");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Count is " + repository.count());
		Thread.sleep(100);
		((Closeable) context).close();
	}

}
