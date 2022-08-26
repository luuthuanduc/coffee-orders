package com.aimatrix.controller;

import com.aimatrix.dto.CoffeeOrderDTO;
import com.aimatrix.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/coffee_orders")
@Slf4j
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrderDTO newOrder(@RequestBody CoffeeOrderDTO coffeeOrderDTO) {
        log.info("Received request for an order");
        return orderService.newOrder(coffeeOrderDTO);
    }

}
