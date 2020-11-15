package polytech.covidalert.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//DO NOT FORGET:
//   bin/zookeeper-server-start.sh config/zookeeper.properties
//   bin/kafka-server-start.sh config/server.properties
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic suspectsTopic() {
        return TopicBuilder.name("suspects").build();
    }
}
