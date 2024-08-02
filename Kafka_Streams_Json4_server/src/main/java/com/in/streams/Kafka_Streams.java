package com.in.streams;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.in.dto.Car;
import com.in.dto.EnrichedUser;
import com.in.dto.User;

import lombok.extern.log4j.Log4j2;
import serde.AppSerdes;
import serde.AppSerdes2;
import serde.AppSerdes3;

@Log4j2
public class Kafka_Streams {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "new-table-app");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
		props.put(StreamsConfig.STATE_DIR_CONFIG, "D:\\kafka-streams");
		props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
//		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AppSerdes3.EnrichedUser().getClass().getName());

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, User> userKStream = builder.stream("user-topic",
				Consumed.with(Serdes.String(), AppSerdes.User()));

		GlobalKTable<String, Car> CarGlobalKTable = builder.globalTable("car-topic",
				Consumed.with(Serdes.String(), AppSerdes2.Car()));
		
		KStream<String, EnrichedUser> enrichedUserStream = userKStream.leftJoin(CarGlobalKTable,
				(userId, user) -> user.getCarId(), (user, car) -> {
					if (car.getCarId() !=null) {
						EnrichedUser er = new EnrichedUser();
						er.setUser(user);
						er.setCar(car);
						log.info("Consumed record with User: {} and Car: {}", user.getFirstName(), car.getName());
						return er;
					} else {
						EnrichedUser er = new EnrichedUser();
						er.setUser(user);
						er.setCar(null);
						return er;
					}
				});

		enrichedUserStream.to("bind-topic", Produced.with(Serdes.String(), AppSerdes3.EnrichedUser()));

		log.info("Consumed record with User: {} and Car: {}", User.class, Car.class);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
