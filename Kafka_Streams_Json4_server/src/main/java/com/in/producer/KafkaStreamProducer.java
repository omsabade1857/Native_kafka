package com.in.producer;

import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.in.dto.Car;
import com.in.dto.User;

import lombok.extern.log4j.Log4j2;
import serde.JsonSerializer;

@Log4j2
public class KafkaStreamProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        

        Producer<String, Object> producer = new KafkaProducer<>(props);
        Scanner scan = new Scanner(System.in);

        User user = new User();

        System.out.print("Enter User Id: ");
        user.setUserId(scan.next());

        System.out.print("Enter first name: ");
        user.setFirstName(scan.next());

        System.out.print("Enter last name: ");
        user.setLastName(scan.next());

        System.out.print("Enter email: ");
        user.setEmail(scan.next());

        System.out.println("\nAvailable cars in store:");
        System.out.println("Id: 1  Car: Toyota Corolla - Sedan             Price: 1000");
        System.out.println("Id: 2  Car: Ford F-150 - Truck                 Price: 5000");
        System.out.println("Id: 3  Car: Honda CR-V - SUV                   Price: 3000");
        System.out.println("Id: 4  Car: Mazda MX-5 Miata - Convertible     Price: 4000");
        System.out.println("Id: 5  Car: Chevrolet Bolt EV - Electric Vehicle (EV) Price: 600");
        System.out.println("Id: 6  Car: Subaru Outback - Wagon             Price: 7000");
        System.out.println("Id: 7  Car: Jeep Wrangler - Off-road Vehicle   Price: 2000");

        Car p1 = null;
        while (p1 == null) {
            System.out.print("Enter Car Id: ");
            String id = scan.next().trim();
            user.setCarId(id);
            switch (id) {
                case "1":
                    p1 = new Car("1", "Toyota Corolla", "Sedan", 1000);
                    break;
                case "2":
                    p1 = new Car("2", "Ford F-150", "Truck", 5000);
                    break;
                case "3":
                    p1 = new Car("3", "Honda CR-V", "SUV", 3000);
                    break;
                case "4":
                    p1 = new Car("4", "Mazda MX-5 Miata", "Convertible", 4000);
                    break;
                case "5":
                    p1 = new Car("5", "Chevrolet Bolt EV", "Electric Vehicle", 600);
                    break;
                case "6":
                    p1 = new Car("6", "Subaru Outback", "Wagon", 7000);
                    break;
                case "7":
                    p1 = new Car("7", "Jeep Wrangler", "Off-road Vehicle", 2000);
                    break;
                default:
                    System.out.println("Invalid Car Id. Please select a valid car.");
                    p1 = null;
                    break;
            }
        }

        ProducerRecord<String, Object> userRecord = new ProducerRecord<>("user-topic", user.getUserId(), user);
        producer.send(userRecord, (success, ex) -> {
            if (ex == null) {
                log.info("User details sent successfully: " + user);
            } else {
                log.error("Error sending user details: " + user, ex);
            }
        });

        Car p2=p1;
        ProducerRecord<String, Object> carRecord = new ProducerRecord<>("car-topic", p1.getCarId(), p1);
        producer.send(carRecord, (success, ex) -> {
            if (ex == null) {
                log.info("Car details sent successfully: " + p2);
            } else {
                log.error("Error sending car details: " + p2, ex);
            }
        });

        scan.close();
        producer.close();
    }
}
