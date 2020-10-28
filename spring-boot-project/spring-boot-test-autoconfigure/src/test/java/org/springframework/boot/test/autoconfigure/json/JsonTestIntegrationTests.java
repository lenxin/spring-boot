package org.springframework.boot.test.autoconfigure.json;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.app.ExampleBasicObject;
import org.springframework.boot.test.autoconfigure.json.app.ExampleCustomObject;
import org.springframework.boot.test.autoconfigure.json.app.ExampleJsonApplication;
import org.springframework.boot.test.autoconfigure.json.app.ExampleJsonObjectWithView;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JsonTest @JsonTest}.
 *



 */
@JsonTest
@ContextConfiguration(classes = ExampleJsonApplication.class)
class JsonTestIntegrationTests {

	@Autowired
	private BasicJsonTester basicJson;

	@Autowired
	private JacksonTester<ExampleBasicObject> jacksonBasicJson;

	@Autowired
	private JacksonTester<ExampleJsonObjectWithView> jacksonWithViewJson;

	@Autowired
	private JacksonTester<ExampleCustomObject> jacksonCustomJson;

	@Autowired
	private GsonTester<ExampleBasicObject> gsonJson;

	@Autowired
	private JsonbTester<ExampleBasicObject> jsonbJson;

	@Test
	void basicJson() {
		assertThat(this.basicJson.from("{\"a\":\"b\"}")).hasJsonPathStringValue("@.a");
	}

	@Test
	void jacksonBasic() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.jacksonBasicJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void jacksonCustom() throws Exception {
		ExampleCustomObject object = new ExampleCustomObject("spring");
		assertThat(this.jacksonCustomJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void gson() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.gsonJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void jsonb() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.jsonbJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void customView() throws Exception {
		ExampleJsonObjectWithView object = new ExampleJsonObjectWithView();
		object.setValue("spring");
		JsonContent<ExampleJsonObjectWithView> content = this.jacksonWithViewJson
				.forView(ExampleJsonObjectWithView.TestView.class).write(object);
		assertThat(content).doesNotHaveJsonPathValue("id");
		assertThat(content).isEqualToJson("example.json");
	}

}
