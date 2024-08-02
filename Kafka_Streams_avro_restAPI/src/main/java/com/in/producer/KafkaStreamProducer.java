package com.in.producer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.in.dto.Employee;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class KafkaStreamProducer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.99.223:18081");

		Producer<Integer, Employee> producer = new KafkaProducer<>(props);
		List<Employee> list = new ArrayList<Employee>();
		Scanner scan = new Scanner(System.in);
		String choice;
		Employee emp = new Employee();
		do {
			System.out.println();
			System.out.print("Enter Id: ");
			emp.setId(scan.nextInt());

			System.out.print("Enter first name: ");
			emp.setFirstName(scan.next());

			System.out.print("Enter last name: ");
			emp.setLastName(scan.next());

			System.out.print("Enter email :");
			emp.setEmail(scan.next());

			list.add(emp);

			System.out.print("Do you want to add another person? (1 for yes, 0 for no): ");
			choice = scan.next();

			if (choice.equals("0")) {
				System.out.println("contine...");
				break;
			}

		} while (!choice.equals("0"));
		int i = 1;
		for (Employee data : list) {
			ProducerRecord<Integer, Employee> record = new ProducerRecord<>("streams-topic",data.getId(), data);

			producer.send(record, (sucess, ex) -> {
				if (ex == null) {
					log.info(String.format("Employee details: %s", data));
				} else {
					log.error("Error: " + data, ex);
				}
			});
		}
		scan.close();
		producer.close();
	}

}
