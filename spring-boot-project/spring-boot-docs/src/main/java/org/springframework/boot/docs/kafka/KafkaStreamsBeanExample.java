package org.springframework.boot.docs.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

/**
 * Example to show usage of {@link StreamsBuilder}.
 *

 */
public class KafkaStreamsBeanExample {

	// tag::configuration[]
	@Configuration(proxyBeanMethods = false)
	@EnableKafkaStreams
	public static class KafkaStreamsExampleConfiguration {

		@Bean
		public KStream<Integer, String> kStream(StreamsBuilder streamsBuilder) {
			KStream<Integer, String> stream = streamsBuilder.stream("ks1In");
			stream.map((k, v) -> new KeyValue<>(k, v.toUpperCase())).to("ks1Out",
					Produced.with(Serdes.Integer(), new JsonSerde<>()));
			return stream;
		}

	}
	// end::configuration[]

}
