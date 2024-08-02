package com.in.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.in.dto.EnrichedUser;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class KafkaStreamConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.223:19092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer01-group");
//        props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        Consumer<String, EnrichedUser> consumer = new KafkaConsumer<>(props);

        try {
            consumer.subscribe(Collections.singletonList("bind-topic"));

            while (true) {
                ConsumerRecords<String, EnrichedUser> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, EnrichedUser> record : records) {
                    log.info("Consumed record with key {} and value {}", record.key(), record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
