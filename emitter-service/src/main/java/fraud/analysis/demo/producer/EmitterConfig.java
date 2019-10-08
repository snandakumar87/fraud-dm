package fraud.analysis.demo.producer;

import java.util.Properties;
import java.util.logging.Logger;

import fraud.analysis.demo.transaction.fraud.analysis.Serde.TransactionSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;

public class EmitterConfig {

	private static final Logger LOGGER = Logger.getLogger(EmitterConfig.class.getName());

	private final String bootstrapServers;
	private final String topic;
	private final String acks;
	private final String securityProtocol;
	private final String serializerClass;

	public EmitterConfig(String bootstrapServers, String topic, String securityProtocol,
						 String serializerClass, String acks) {
		this.bootstrapServers = bootstrapServers;
		this.topic = topic;
		this.securityProtocol = securityProtocol;
		this.serializerClass = serializerClass;
		this.acks = acks;
	}

	public static EmitterConfig fromEnv() {
		String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
		LOGGER.info("BOOTSTRAP_SERVERS: " + bootstrapServers);

		String topic = System.getenv("PRODUCER_TOPIC");
		LOGGER.info("PRODUCER_TOPIC: {}" + topic);

		String securityProtocol = System.getenv("SECURITY_PROTOCOL");
		LOGGER.info("SECURITY_PROTOCOL: {}" + securityProtocol);

		String serializerClass = System.getenv("SERIALIZER_CLASS");
		LOGGER.info("SERIALIZER_CLASS: {}" + serializerClass);

		String acks = System.getenv("ACKS");
		LOGGER.info("ACKS: {}" + acks);

		return new EmitterConfig(bootstrapServers, topic, securityProtocol, serializerClass, acks);
	}

	public static Properties createProperties(EmitterConfig config) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
//		props.put(ProducerConfig.ACKS_CONFIG, config.getAcks());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, config.getSecurityProtocol());

		return props;
	}

	public String getAcks() {
		return acks;
	}

	public String getBootstrapServers() {
		return bootstrapServers;
	}

	public String getTopic() {
		return topic;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public String getSerializerClass() {
		return serializerClass;
	}

}
