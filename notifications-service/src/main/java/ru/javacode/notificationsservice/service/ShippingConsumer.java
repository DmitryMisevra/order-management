package ru.javacode.notificationsservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.javacode.models.Order;

@Service
public class ShippingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ShippingConsumer.class);

    @KafkaListener(topics = "${kafka.topic.sent-orders.name}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Order order) {
        try {
            logger.info("Order {} has been shipped. Notifying user {}", order.getOrderId(), order.getUserId());
            // Логика отправки уведомления пользователю
            // Например, отправка email или push-уведомления
        } catch (Exception e) {
            logger.error("Error notifying user for order {}", order.getOrderId(), e);
            // Обработка ошибок
        }
    }
}
