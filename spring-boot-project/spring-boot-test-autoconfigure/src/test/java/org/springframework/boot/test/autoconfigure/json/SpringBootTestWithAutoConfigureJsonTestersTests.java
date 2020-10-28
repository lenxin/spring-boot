package org.springframework.boot.test.autoconfigure.json;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.app.ExampleBasicObject;
import org.springframework.boot.test.autoconfigure.json.app.ExampleJsonApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SpringBootTest @SpringBootTest} with
 * {@link AutoConfigureJsonTesters @AutoConfigureJsonTesters}.
 *

 */
@SpringBootTest
@AutoConfigureJsonTesters
@ContextConfiguration(classes = ExampleJsonApplication.class)
class SpringBootTestWithAutoConfigureJsonTestersTests {

	@Autowired
	private BasicJsonTester basicJson;

	@Autowired
	private JacksonTester<ExampleBasicObject> jacksonTester;

	@Autowired
	private GsonTester<ExampleBasicObject> gsonTester;

	@Autowired
	private JsonbTester<ExampleBasicObject> jsonbTester;

	@Test
	void contextLoads() {
		assertThat(this.basicJson).isNotNull();
		assertThat(this.jacksonTester).isNotNull();
		assertThat(this.jsonbTester).isNotNull();
		assertThat(this.gsonTester).isNotNull();
	}

}
