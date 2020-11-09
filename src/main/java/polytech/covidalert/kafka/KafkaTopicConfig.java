package polytech.covidalert.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//DO NOT FORGET:
//   ~/kafka_2.12-2.6.0$ bin/zookeeper-server-start.sh config/zookeeper.properties
//   ~/kafka_2.12-2.6.0$ bin/kafka-server-start.sh config/server.properties
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("mytopic-1").build();
    }
    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("mytopic-2").build();
    }
}
