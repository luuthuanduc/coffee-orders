package com.aimatrix.service;

import com.aimatrix.domain.generated.Address;
import com.aimatrix.domain.generated.CoffeeOrder;
import com.aimatrix.domain.generated.OrderLineItem;
import com.aimatrix.domain.generated.Store;
import com.aimatrix.dto.AddressDTO;
import com.aimatrix.dto.CoffeeOrderDTO;
import com.aimatrix.dto.OrderLineItemDTO;
import com.aimatrix.dto.StoreDTO;
import com.aimatrix.producer.OrderProducer;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class OrderService {

    final OrderProducer orderProducer;

    public OrderService(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public CoffeeOrderDTO newOrder(CoffeeOrderDTO coffeeOrderDTO) {
        CoffeeOrder coffeeOrder = of(coffeeOrderDTO);
        coffeeOrderDTO.setId(coffeeOrder.getId());
        orderProducer.sendMessage(coffeeOrder);
        return coffeeOrderDTO;
    }

    private CoffeeOrder of(CoffeeOrderDTO coffeeOrderDTO) {
        Instant instant = Instant.now();
        Timestamp orderedTime = Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).setNanos(instant.getNano()).build();

        return CoffeeOrder.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(coffeeOrderDTO.getName())
                .setStore(of(coffeeOrderDTO.getStore()))
                .addAllOrderLineItems(of(coffeeOrderDTO.getOrderLineItems()))
                .setStatus(coffeeOrderDTO.getStatus())
                .setOrderedTime(orderedTime)
                .setPickUp(coffeeOrderDTO.getPickUp())
                .setStatus(coffeeOrderDTO.getStatus())
                .build();
    }

    private List<OrderLineItem> of(List<OrderLineItemDTO> orderLineItemDTOList) {
        return orderLineItemDTOList.stream().map(this::of).collect(Collectors.toList());
    }

    private OrderLineItem of(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.newBuilder()
                .setName(orderLineItemDTO.getName())
                .setSize(orderLineItemDTO.getSize())
                .setQuantity(orderLineItemDTO.getQuantity())
                .setCost(orderLineItemDTO.getCost().doubleValue())
                .build();
    }

    private Store of(StoreDTO storeDTO) {
        return Store.newBuilder()
                .setId(storeDTO.getStoreId())
                .setAddress(of(storeDTO.getAddress()))
                .build();
    }

    private Address of(AddressDTO addressDTO) {
        return Address.newBuilder()
                .setAddressLine1(addressDTO.getAddressLine1())
                .setCity(addressDTO.getCity())
                .setStateProvince(addressDTO.getState())
                .setCountry(addressDTO.getCountry())
                .setZip(addressDTO.getZip())
                .build();
    }

}
