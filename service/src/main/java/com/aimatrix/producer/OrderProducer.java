package com.aimatrix.producer;

import com.aimatrix.domain.generated.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class OrderProducer {

    @Value("${spring.kafka.template.default-topic}")
    String topic;
    final KafkaTemplate<String, CoffeeOrder> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, CoffeeOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CoffeeOrder coffeeOrder) {
        ProducerRecord<String, CoffeeOrder> producerRecord = new ProducerRecord<>(topic, coffeeOrder.getId(), coffeeOrder);
        ListenableFuture<SendResult<String, CoffeeOrder>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                handleFailure(coffeeOrder, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, CoffeeOrder> result) {
                handleSuccess(coffeeOrder, result);
            }
        });
    }

    private void handleFailure(CoffeeOrder coffeeOrder, Throwable throwable) {
        log.error("Error sending the message for {} and the exception is {}", coffeeOrder, throwable.getMessage());
    }

    private void handleSuccess(CoffeeOrder coffeeOrder, SendResult<String, CoffeeOrder> result) {
        log.info("Message sent successFully for the key : {} and the value is {} , partition is {}",
                coffeeOrder.getId(), coffeeOrder, result.getRecordMetadata().partition());
    }

}
