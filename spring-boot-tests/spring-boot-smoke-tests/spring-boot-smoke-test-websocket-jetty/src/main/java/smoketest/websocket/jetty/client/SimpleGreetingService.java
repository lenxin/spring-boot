package smoketest.websocket.jetty.client;

public class SimpleGreetingService implements GreetingService {

	@Override
	public String getGreeting() {
		return "Hello world!";
	}

}
