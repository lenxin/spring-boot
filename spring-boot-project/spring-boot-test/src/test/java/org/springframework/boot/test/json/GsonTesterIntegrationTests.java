package org.springframework.boot.test.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link GsonTester}. Shows typical usage.
 *


 */
class GsonTesterIntegrationTests {

	private GsonTester<ExampleObject> simpleJson;

	private GsonTester<List<ExampleObject>> listJson;

	private GsonTester<Map<String, Integer>> mapJson;

	private GsonTester<String> stringJson;

	private Gson gson;

	private static final String JSON = "{\"name\":\"Spring\",\"age\":123}";

	@BeforeEach
	void setup() {
		this.gson = new Gson();
		GsonTester.initFields(this, this.gson);
	}

	@Test
	void typicalTest() throws Exception {
		String example = JSON;
		assertThat(this.simpleJson.parse(example).getObject().getName()).isEqualTo("Spring");
	}

	@Test
	void typicalListTest() throws Exception {
		String example = "[" + JSON + "]";
		assertThat(this.listJson.parse(example)).asList().hasSize(1);
		assertThat(this.listJson.parse(example).getObject().get(0).getName()).isEqualTo("Spring");
	}

	@Test
	void typicalMapTest() throws Exception {
		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		assertThat(this.mapJson.write(map)).extractingJsonPathNumberValue("@.a").isEqualTo(1);
	}

	@Test
	void stringLiteral() throws Exception {
		String stringWithSpecialCharacters = "myString";
		assertThat(this.stringJson.write(stringWithSpecialCharacters)).extractingJsonPathStringValue("@")
				.isEqualTo(stringWithSpecialCharacters);
	}

}
