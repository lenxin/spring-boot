package smoketest.atomikos;

import javax.transaction.Transactional;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {

	private final JmsTemplate jmsTemplate;

	private final AccountRepository accountRepository;

	public AccountService(JmsTemplate jmsTemplate, AccountRepository accountRepository) {
		this.jmsTemplate = jmsTemplate;
		this.accountRepository = accountRepository;
	}

	public void createAccountAndNotify(String username) {
		this.jmsTemplate.convertAndSend("accounts", username);
		this.accountRepository.save(new Account(username));
		if ("error".equals(username)) {
			throw new RuntimeException("Simulated error");
		}
	}

}
