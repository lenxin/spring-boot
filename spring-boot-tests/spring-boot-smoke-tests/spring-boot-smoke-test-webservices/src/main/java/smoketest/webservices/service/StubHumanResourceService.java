package smoketest.webservices.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;

@Service
public class StubHumanResourceService implements HumanResourceService {

	private final Log logger = LogFactory.getLog(StubHumanResourceService.class);

	@Override
	public void bookHoliday(Date startDate, Date endDate, String name) {
		this.logger.info("Booking holiday for [" + startDate + " - " + endDate + "] for [" + name + "]");
	}

}
