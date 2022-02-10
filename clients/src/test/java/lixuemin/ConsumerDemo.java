package lixuemin;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConsumerDemo {
    private static final String brokerList = "localhost:9092";
    private static final String TOPIC = "quickstart-events";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemo.class);


    /**
     * 1. 消费者需要配置的是deserializer
     * 2. 消费者需要额外指定消费者组
     * 3. 注意properties中key均需要设为ConsumerConfig类中定义常量
     * */
    private static Properties initConsumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, TOPIC);
        return props;
    }

    public static void main(String[] args) {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(initConsumerConfig());
        try {
            kafkaConsumer.subscribe(Arrays.asList(TOPIC));
            while (true) {
                final ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofDays(1));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("111111111111111111111111111111");
                    System.out.println(record.toString());
                    System.out.println("111111111111111111111111111111");
                }
            }
        } catch (Exception e) {
            LOGGER.error("consumer error", e);
        }finally {
            kafkaConsumer.close();
        }
    }
}
