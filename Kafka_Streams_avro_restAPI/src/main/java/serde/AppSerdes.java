package serde;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.in.dto.Employee;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class AppSerdes extends Serdes {

    public static Serde<Employee> employee() {
        Serde<Employee> serde = new SpecificAvroSerde<>();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put("schema.registry.url", "http://192.168.99.223:18081");
        serde.configure(serdeConfigs, false);

        return serde;
    }
}
