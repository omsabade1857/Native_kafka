package com.in.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in.consumer.KafkaConsumer1;
import com.in.dto.Car;
import com.in.dto.User;
import com.in.producer.KafkaProducer1;

import lombok.extern.log4j.Log4j2;

//@RequestMapping("/emp")
@Log4j2
@RestController
public class KafkaController {

	private KafkaProducer1 kafkaProducer;
	private KafkaStreams kafkaStreams;

	public KafkaController(KafkaProducer1 kafkaProducer, KafkaStreams kafkaStreams) {
		this.kafkaProducer = kafkaProducer;
		this.kafkaStreams = kafkaStreams;
	}

	@PostMapping("/send")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		log.info("Received request to create user: {}", user);
		if (user != null) {
			try {
				kafkaProducer.send(user);
				return ResponseEntity.status(HttpStatus.CREATED).body("User created!");
			} catch (Exception e) {
				log.error("Error: ", e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
	}

	@GetMapping("/{key}")
	public ResponseEntity<?> getUser(@PathVariable("key") Integer key) {
		try {
			ReadOnlyKeyValueStore<Integer, User> keyValueStore = kafkaStreams.store(
					StoreQueryParameters.fromNameAndType("User_State_Store", QueryableStoreTypes.keyValueStore()));

			User user = keyValueStore.get(key);
			if (user == null) {
				return new ResponseEntity<>("Not found User with id : " + key, HttpStatus.NOT_FOUND);
			}
			Car car = user.getCar();
			if (car != null) {
				car.setUserId(user.getUserId()); // set the car User
			}
			return ResponseEntity.ok(user.toString());

		} catch (Exception e) {
			log.error("Error while retrieving user with ID: " + key, e);
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
