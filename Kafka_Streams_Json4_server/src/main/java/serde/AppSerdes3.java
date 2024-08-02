package serde;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.in.dto.EnrichedUser;

public class AppSerdes3 extends Serdes {

	static final class EnrichedUserSerde extends Serdes.WrapperSerde<EnrichedUser> {
		EnrichedUserSerde() {
			super(new JsonSerializer<>(), new JsonDeserializer<>());
		}
	}

	public static Serde<EnrichedUser> EnrichedUser() {
		EnrichedUserSerde serde = new EnrichedUserSerde();

		Map<String, Object> serdeConfigs = new HashMap<>();
		serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, EnrichedUser.class);
		serde.configure(serdeConfigs, false);

		return serde;
	}
}
