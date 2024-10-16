package ru.javacode.paymentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.javacode.models.Order;

@Service
public class OrderConsumer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String PAYED_ORDERS_TOPIC = "payed_orders";
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    public OrderConsumer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${kafka.topic.new-orders.name}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Order order) {
        try {
            logger.info("Received new order: {}", order.getOrderId());
            // Логика обработки оплаты
            order.setStatus("PAYED");
            // Отправляем информацию об успешной оплате
            kafkaTemplate.send(PAYED_ORDERS_TOPIC, order.getOrderId(), order);
            logger.info("Order {} processed and sent to topic {}", order.getOrderId(), PAYED_ORDERS_TOPIC);
        } catch (Exception e) {
            logger.error("Error processing order {}", order.getOrderId(), e);
            // Механизм повторной отправки или обработки ошибок
        }
    }
}
