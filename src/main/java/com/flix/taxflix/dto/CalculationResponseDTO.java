package com.flix.taxflix.dto;


import com.flix.taxflix.enums.Country;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CalculationResponseDTO {

    private final Integer calculationId;

    private final Double totalNetItemsPrice;

    private final Double TotalItemsTaxParentage;

    private final Double totalItemsGrossPrice;

    private final List<CalculationItemResponse> calculationItemResponses;
}
