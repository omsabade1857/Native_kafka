package com.in.consumer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.in.dto.Employee;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KafkaConsumer1 {
	private static ConcurrentMap<String, Employee> employeeMap= new ConcurrentHashMap<String, Employee>();
	
	@KafkaListener(topics = "Spring_kafka_Streams01", groupId = "consumer04_group")
	public void read(ConsumerRecord<Integer, Employee> consumerRecord) {
		employeeMap.put(consumerRecord.value().firstName.toString(), consumerRecord.value());
		log.info(String.format("key :%s, value: %s", consumerRecord.key(), consumerRecord.value()));
	}
	
	public Employee getEmployees(String name) {
        return employeeMap.get(name);
    }
}
