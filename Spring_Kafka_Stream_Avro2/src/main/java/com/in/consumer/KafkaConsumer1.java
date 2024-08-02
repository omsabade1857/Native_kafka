package com.in.consumer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.in.dto.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KafkaConsumer1 {
	
	@KafkaListener(topics = "Spring_User", groupId = "consumer_group")
	public void read(ConsumerRecord<Integer, User> consumerRecord) {
		System.out.println(String.format("Consumed with-> key :%s, value: %s", consumerRecord.key(), consumerRecord.value()));
	}
}
