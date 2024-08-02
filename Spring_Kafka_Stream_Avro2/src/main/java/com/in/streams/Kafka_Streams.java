package com.in.streams;

import java.util.Properties;

import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;

import com.in.dto.Car;
import com.in.dto.User;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import serde.AppSerdes;

public class Kafka_Streams {

	private Properties properties;

	public Kafka_Streams(Properties properties) {
		this.properties = properties;
	}

	public KafkaStreams createStreams() {

		StreamsBuilder builder = new StreamsBuilder();

		GlobalKTable<Integer, User> GTable = builder.globalTable("Spring_User",
				Consumed.with(AppSerdes.Integer(), AppSerdes.User()),
				Materialized.<Integer, User, KeyValueStore<Bytes, byte[]>>as("User_State_Store")
						.withKeySerde(AppSerdes.Integer()).withValueSerde(AppSerdes.User()));

		KafkaStreams streams = new KafkaStreams(builder.build(), properties);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
		return streams;

	}
}
