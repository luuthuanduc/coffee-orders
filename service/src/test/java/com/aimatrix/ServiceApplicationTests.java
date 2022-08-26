package com.aimatrix;

import com.aimatrix.domain.generated.PickUp;
import com.aimatrix.domain.generated.Size;
import com.aimatrix.dto.AddressDTO;
import com.aimatrix.dto.CoffeeOrderDTO;
import com.aimatrix.dto.OrderLineItemDTO;
import com.aimatrix.dto.StoreDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ServiceApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        AddressDTO addressDTO = AddressDTO.builder()
                .addressLine1("1234 street")
                .city("Chicago")
                .state("Illinois")
                .country("USA")
                .zip("11244")
                .build();
        StoreDTO storeDTO = StoreDTO.builder()
                .storeId(123)
                .address(addressDTO)
                .build();
        OrderLineItemDTO orderLineItemDTO = OrderLineItemDTO.builder()
                .name("Latte")
                .size(Size.MEDIUM)
                .quantity(1)
                .cost(new BigDecimal("3.99"))
                .build();
        CoffeeOrderDTO coffeeOrderDTO = CoffeeOrderDTO.builder()
                .id(UUID.randomUUID().toString())
                .name("Dilip Sundarraj")
                .nickName("")
                .store(storeDTO)
                .orderLineItems(List.of(orderLineItemDTO))
                .pickUp(PickUp.IN_STORE)
                .status("NEW")
                .build();
        System.out.println("coffeeOrderJSON : " + objectMapper.writeValueAsString(coffeeOrderDTO));
    }

}
