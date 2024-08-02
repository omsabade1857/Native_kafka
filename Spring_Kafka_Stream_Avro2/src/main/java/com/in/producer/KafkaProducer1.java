package com.in.producer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.in.dto.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KafkaProducer1 {

//	@Value("${topic.name}")
//	private String topic;

	@Autowired
	private KafkaTemplate<Integer, User> kafkaTemplate;

	public void send(User User) {
		CompletableFuture<SendResult<Integer, User>> future = kafkaTemplate.send("Spring_User", User.getUserId(), User);

		future.whenComplete((result, ex) -> {
			if (ex == null) {
				System.out.println("Sent message [user :" + User + "] offset:" + result.getRecordMetadata().offset()
						+ " partitions: " + result.getRecordMetadata().partition());
				if (User.getCar() != null) {
					System.out.println("[Car: " + User.getCar() + "] offset:" + result.getRecordMetadata().offset()
							+ " partitions: " + result.getRecordMetadata().partition());
				} else {
					System.out.println("Car is empty!! please enter the data");
				}
			} else {
				log.error("Error:" + ex.getMessage());
			}
		});

	}
}
