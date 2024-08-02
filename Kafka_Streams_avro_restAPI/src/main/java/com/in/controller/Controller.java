package com.in.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.dto.Employee;
import com.in.streams.Kafka_Streams;

@RestController
public class Controller {
	
	private KafkaStreams kafkaStreams;
	
		public Controller(KafkaStreams kafkaStreams) {
		this.kafkaStreams = kafkaStreams;
	}


	@GetMapping("/{key}")
	public ResponseEntity<?> getEntity(@PathVariable("key") Integer key) {

		ReadOnlyKeyValueStore<Integer, Employee> keyValueStore = kafkaStreams
				.store(StoreQueryParameters.fromNameAndType("Employee_Store", QueryableStoreTypes.keyValueStore()));

		Employee employee = keyValueStore.get(key);
		System.out.println(employee);
		if (employee == null) {
			return new ResponseEntity<>("Not found employee with id : " + key, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(String.format("Employee [id=%d, firstName=%s, lastName=%s, email=%s]",
				employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()), HttpStatus.OK);
	}

}
