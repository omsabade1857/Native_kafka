package serde;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.in.dto.User;

public class AppSerdes extends Serdes {

	static final class UserSerde extends Serdes.WrapperSerde<User> {
		UserSerde() {
			super(new JsonSerializer<>(), new JsonDeserializer<>());
		}
	}

	public static Serde<User> User() {
		UserSerde serde = new UserSerde();

		Map<String, Object> serdeConfigs = new HashMap<>();
		serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, User.class);
		serde.configure(serdeConfigs, false);

		return serde;
	}
}
