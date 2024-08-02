package com.in.streams;

import java.util.Properties;

import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;

import com.in.dto.Employee;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import serde.AppSerdes;

public class Kafka_Streams {

//	@Value("${topic.name}")
//	private String topic;

	public KafkaStreams createStreams() {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Spring_kafka_app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.223:19092");
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.99.223:18081");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, AppSerdes.Integer().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AppSerdes.employee().getClass().getName());
		props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "10000"); // Adjust commit interval
		props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "10485760"); // 10MB cache

		StreamsBuilder builder = new StreamsBuilder();
		
		GlobalKTable<Integer, Employee> kTable = builder.globalTable("Spring_kafka_Streams01",
				Consumed.with(AppSerdes.Integer(), AppSerdes.employee()),
				Materialized.<Integer, Employee, KeyValueStore<Bytes, byte[]>>as("New_State_Store01")
						.withKeySerde(AppSerdes.Integer())
						.withValueSerde(AppSerdes.employee()));

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
		return streams;

	}
}
