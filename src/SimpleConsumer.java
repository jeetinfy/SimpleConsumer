import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class SimpleConsumer {
	public static String TOPIC;
	public static String BOOTSTRAP_SERVER;

	public static void main(String[] args) {

		// Check arguments length value
		if (args.length < 2) {
			System.out.println("Syntax");
			System.out.println("SimpleConsumer.jar <Topic> <Bootstrap Server>");
			return;
		}

		TOPIC = args[0];
		BOOTSTRAP_SERVER = args[1];

		System.out.println("BOOTSTRAP_SERVER: " + BOOTSTRAP_SERVER);
		System.out.println("TOPIC: " + TOPIC);

		Properties props = new Properties();

		props.put("bootstrap.servers", BOOTSTRAP_SERVER + ":9092");

		props.put("group.id", "test-" + new Date().getTime());
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		// Kafka Consumer subscribes list of topics here.
		 consumer.subscribe(Arrays.asList(TOPIC));

//		TopicPartition partition0 = new TopicPartition(TOPIC, 0);
//		consumer.assign(Arrays.asList(partition0));

		// print the topic name
		System.out.println("Subscribed to topic " + TOPIC);

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				// print the offset,key and value for the consumer records.
//				System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n", record.partition(), record.offset(), record.key(), record.value());
				System.out.printf("%s\n", record.value());
			}
		}
	}
}