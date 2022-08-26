package com.aimatrix.dto;

import com.aimatrix.domain.generated.PickUp;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CoffeeOrderDTO {

    private String id;
    private String name;
    private String nickName;
    private StoreDTO store;
    private List<OrderLineItemDTO> orderLineItems;
    private PickUp pickUp;
    private String status;

}
