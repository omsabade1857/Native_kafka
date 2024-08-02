package com.in.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.consumer.KafkaConsumer1;
import com.in.dto.Employee;
import com.in.producer.KafkaProducer1;

@RestController
//@RequestMapping("/emp")
public class KafkaController {

	private KafkaProducer1 kafkaProducer;
	private KafkaStreams kafkaStreams;
	private KafkaConsumer1 kafkaConsumer;

	public KafkaController(KafkaProducer1 kafkaProducer, KafkaStreams kafkaStreams, KafkaConsumer1 kafkaConsumer) {
		this.kafkaProducer = kafkaProducer;
		this.kafkaStreams = kafkaStreams;
		this.kafkaConsumer = kafkaConsumer;
	}

	@PostMapping("/send")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		System.out.println(employee);
		if (employee != null) {
			kafkaProducer.send(employee);
			return ResponseEntity.status(HttpStatus.CREATED).body("Employee created !!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please check data !!");
	}

	@GetMapping("/{key}")
	public ResponseEntity<?> getEmployee(@PathVariable("key") Integer key) {

		ReadOnlyKeyValueStore<Integer, Employee> keyValueStore = kafkaStreams
				.store(StoreQueryParameters.fromNameAndType("New_State_Store01", QueryableStoreTypes.keyValueStore()));

		Employee employee = keyValueStore.get(key);
		System.out.println(employee);
		if (employee == null) {
			return new ResponseEntity<>("Not found employee with id : " + key, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(String.format("Employee [id=%d, firstName=%s, lastName=%s, email=%s]",
				employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()), HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<?> getEmployee1(@PathVariable("name") String name) {
		Employee employee = kafkaConsumer.getEmployees(name.toLowerCase());
		if (employee == null) {
			return new ResponseEntity<>("Not found employee with name : " + name, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(String.format("Employee [id=%d, firstName=%s, lastName=%s, email=%s]",
				employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()), HttpStatus.OK);
	}
}
