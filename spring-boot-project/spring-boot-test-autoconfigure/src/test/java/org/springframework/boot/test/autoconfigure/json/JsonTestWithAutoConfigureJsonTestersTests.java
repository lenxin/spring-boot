package org.springframework.boot.test.autoconfigure.json;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.app.ExampleBasicObject;
import org.springframework.boot.test.autoconfigure.json.app.ExampleJsonApplication;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JsonTest @JsonTest} with
 * {@link AutoConfigureJsonTesters @AutoConfigureJsonTesters}.
 *

 */
@JsonTest
@AutoConfigureJsonTesters(enabled = false)
@ContextConfiguration(classes = ExampleJsonApplication.class)
class JsonTestWithAutoConfigureJsonTestersTests {

	@Autowired(required = false)
	private BasicJsonTester basicJson;

	@Autowired(required = false)
	private JacksonTester<ExampleBasicObject> jacksonTester;

	@Autowired(required = false)
	private GsonTester<ExampleBasicObject> gsonTester;

	@Autowired(required = false)
	private JsonbTester<ExampleBasicObject> jsonbTester;

	@Test
	void basicJson() {
		assertThat(this.basicJson).isNull();
	}

	@Test
	void jackson() {
		assertThat(this.jacksonTester).isNull();
	}

	@Test
	void gson() {
		assertThat(this.gsonTester).isNull();
	}

	@Test
	void jsonb() {
		assertThat(this.jsonbTester).isNull();
	}

}
