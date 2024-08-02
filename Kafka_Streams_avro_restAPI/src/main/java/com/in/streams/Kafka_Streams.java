package com.in.streams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import com.in.dto.Employee;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import lombok.extern.log4j.Log4j2;
import serde.AppSerdes;

@Log4j2
public class Kafka_Streams {
	public KafkaStreams createStreams() {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Kafka_Streams");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.99.223:18081");
//		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AppSerdes.employee().getClass().getName());

		StreamsBuilder builder = new StreamsBuilder();
		KTable<Integer, Employee> kTable = builder.table("streams-topic",
				Consumed.with(AppSerdes.Integer(), AppSerdes.employee()),
				Materialized.<Integer, Employee, KeyValueStore<Bytes, byte[]>>as("Employee_Store")
						.withKeySerde(AppSerdes.Integer())
						.withValueSerde(AppSerdes.employee()));

		/*
		 * // Name which are start with 'O' stream.filter((k, v) ->
		 * v.getFirstName().toString().startsWith("O")) .mapValues(val -> "You Name: " +
		 * val.getFirstName() + "==>") .to("name-topic",
		 * Produced.with(AppSerdes.String(), Serdes.String()));
		 * 
		 * // Full name in upperCase stream.map((k, v) -> new KeyValue<>(k,
		 * v.getFirstName().toString().toUpperCase() + " " +
		 * v.getLastName().toString().toUpperCase())) .mapValues(val -> "Name: " +
		 * val).to("new-topic", Produced.with(Serdes.String(), Serdes.String()));
		 */

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
		return streams;

	}
}
