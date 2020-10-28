package smoketest.websocket.undertow.client;

public class SimpleGreetingService implements GreetingService {

	@Override
	public String getGreeting() {
		return "Hello world!";
	}

}
