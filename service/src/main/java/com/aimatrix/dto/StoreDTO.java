package com.aimatrix.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StoreDTO {

    private Integer storeId;
    private AddressDTO address;

}
