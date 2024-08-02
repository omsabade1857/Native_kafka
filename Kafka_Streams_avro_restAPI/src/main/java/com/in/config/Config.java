package com.in.config;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.in.streams.Kafka_Streams;

@Configuration
public class Config {

	@Bean
	public KafkaStreams streams() {
		Kafka_Streams kafka_Streams = new Kafka_Streams();
		return kafka_Streams.createStreams();
	}
}
