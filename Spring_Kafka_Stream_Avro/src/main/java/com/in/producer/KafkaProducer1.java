package com.in.producer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.in.dto.Employee;

@Service
public class KafkaProducer1 {

//	@Value("${topic.name}")
//	private String topic;

	@Autowired
	private KafkaTemplate<Integer, Employee> kafkaTemplate;

	public void send(Employee employee) {
		CompletableFuture<SendResult<Integer, Employee>> future = kafkaTemplate.send("Spring_kafka_Streams01",employee.getId(), employee);

		future.whenComplete((result, ex) -> {
			if (ex == null) {
				System.out.println("Sent message [" + employee + "] offset:" + result.getRecordMetadata().offset()
						+ " partitions: " + result.getRecordMetadata().partition());
			}else {
				System.out.println("Error:"+ex.getMessage());
			}
		});

	}
}
