package ru.javacode.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javacode.models.Order;
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String NEW_ORDERS_TOPIC = "new_orders";

    public OrderController(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        order.setStatus("NEW");
        kafkaTemplate.send(NEW_ORDERS_TOPIC, order.getOrderId(), order);
        return ResponseEntity.ok("Order created successfully");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        // Логика обновления статуса заказа
        return ResponseEntity.ok("Order status updated successfully");
    }
}
