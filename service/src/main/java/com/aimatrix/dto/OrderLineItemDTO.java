package com.aimatrix.dto;

import com.aimatrix.domain.generated.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OrderLineItemDTO {

    private String name;
    private Size size;
    private Integer quantity;
    private BigDecimal cost;

}
