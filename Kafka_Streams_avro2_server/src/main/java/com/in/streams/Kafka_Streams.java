package com.in.streams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.in.dto.Employee;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import lombok.extern.log4j.Log4j2;
import serde.AppSerdes;

@Log4j2
public class Kafka_Streams {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-example");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.99.223:18081");
//		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

		StreamsBuilder builder = new StreamsBuilder();
		System.out.println(JsonDeserializer.class.getName() + " this");

		KStream<String, Employee> stream = builder.stream("streams03-topic",
				Consumed.with(AppSerdes.String(), AppSerdes.employee()));

		
		//Name which are start with 'O'
		stream.filter((k, v) -> v.getFirstName().toString().startsWith("O")).mapValues(val -> "You Name: " + val.toString()+"==>").to("name-topic",
				Produced.with(AppSerdes.String(), AppSerdes.String()));
		
		
		//full name in upperCase
		stream.map((k, v) -> new KeyValue<>(k,v.getFirstName().toString().toUpperCase()+" "+v.getLastName().toString().toUpperCase())).mapValues(val -> "Name: " + val.toString())
				.to("new-topic", Produced.with(Serdes.String(), Serdes.String()));

		stream.peek((k, v) -> log.info(v.toString()));

		Topology topology = builder.build();

		KafkaStreams streams = new KafkaStreams(topology, props);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
