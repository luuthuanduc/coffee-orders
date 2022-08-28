package com.aimatrix.consumer;

import com.aimatrix.domain.generated.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {

    @KafkaListener(topics = {"${spring.kafka.template.default-topic}"})
    public void onMessage(ConsumerRecord<String, CoffeeOrder> consumerRecord) {
        CoffeeOrder coffeeOrder = consumerRecord.value();
        log.info("CoffeeOrder key: {} , value: {}", coffeeOrder.getId(), coffeeOrder);
    }

}
