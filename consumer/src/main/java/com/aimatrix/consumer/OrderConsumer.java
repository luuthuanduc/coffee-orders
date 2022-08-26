package com.aimatrix.consumer;

import com.aimatrix.domain.generated.CoffeeOrder;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {

    @KafkaListener(topics = {"${spring.kafka.template.default-topic}"})
    public void onMessage(ConsumerRecord<String, DynamicMessage> consumerRecord) {
        try {
            CoffeeOrder coffeeOrder =
                    CoffeeOrder.newBuilder().build().getParserForType().parseFrom(consumerRecord.value().toByteArray());
            log.info("ConsumerRecord key: {} , value: {}", coffeeOrder.getId(), coffeeOrder);
        } catch (InvalidProtocolBufferException exception) {
            exception.printStackTrace();
        }
    }

}
