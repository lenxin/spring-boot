package smoketest.jndi;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		this.jmsTemplate.convertAndSend("java:/jms/queue/bootdemo", username);
		Account entity = new Account(username);
		this.accountRepository.save(entity);
		if ("error".equals(username)) {
			throw new RuntimeException("Simulated error");
		}
	}

}
