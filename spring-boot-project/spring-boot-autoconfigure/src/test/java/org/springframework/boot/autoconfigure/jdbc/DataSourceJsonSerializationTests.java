package org.springframework.boot.autoconfigure.jdbc;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Test;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test that a {@link DataSource} can be exposed as JSON for actuator endpoints.
 *

 */
class DataSourceJsonSerializationTests {

	@Test
	void serializerFactory() throws Exception {
		DataSource dataSource = new DataSource();
		SerializerFactory factory = BeanSerializerFactory.instance
				.withSerializerModifier(new GenericSerializerModifier());
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializerFactory(factory);
		String value = mapper.writeValueAsString(dataSource);
		assertThat(value.contains("\"url\":")).isTrue();
	}

	@Test
	void serializerWithMixin() throws Exception {
		DataSource dataSource = new DataSource();
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(DataSource.class, DataSourceJson.class);
		String value = mapper.writeValueAsString(dataSource);
		assertThat(value.contains("\"url\":")).isTrue();
		assertThat(StringUtils.countOccurrencesOf(value, "\"url\"")).isEqualTo(1);
	}

	@JsonSerialize(using = TomcatDataSourceSerializer.class)
	interface DataSourceJson {

	}

	static class TomcatDataSourceSerializer extends JsonSerializer<DataSource> {

		private ConversionService conversionService = new DefaultConversionService();

		@Override
		public void serialize(DataSource value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeStartObject();
			for (PropertyDescriptor property : BeanUtils.getPropertyDescriptors(DataSource.class)) {
				Method reader = property.getReadMethod();
				if (reader != null && property.getWriteMethod() != null
						&& this.conversionService.canConvert(String.class, property.getPropertyType())) {
					jgen.writeObjectField(property.getName(), ReflectionUtils.invokeMethod(reader, value));
				}
			}
			jgen.writeEndObject();
		}

	}

	static class GenericSerializerModifier extends BeanSerializerModifier {

		private ConversionService conversionService = new DefaultConversionService();

		@Override
		public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
				List<BeanPropertyWriter> beanProperties) {
			List<BeanPropertyWriter> result = new ArrayList<>();
			for (BeanPropertyWriter writer : beanProperties) {
				AnnotatedMethod setter = beanDesc.findMethod("set" + StringUtils.capitalize(writer.getName()),
						new Class<?>[] { writer.getType().getRawClass() });
				if (setter != null && this.conversionService.canConvert(String.class, writer.getType().getRawClass())) {
					result.add(writer);
				}
			}
			return result;
		}

	}

}
