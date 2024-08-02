package serde;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.in.dto.Car;

public class AppSerdes2 extends Serdes {

	static final class CarSerde extends Serdes.WrapperSerde<Car> {
		CarSerde() {
			super(new JsonSerializer<>(), new JsonDeserializer<>());
		}
	}

	public static Serde<Car> Car() {
		CarSerde serde = new CarSerde();

		Map<String, Object> serdeConfigs = new HashMap<>();
		serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, Car.class);
		serde.configure(serdeConfigs, false);

		return serde;
	}
}
