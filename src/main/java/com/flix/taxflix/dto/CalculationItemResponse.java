package com.flix.taxflix.dto;


import com.flix.taxflix.enums.Country;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CalculationItemResponse {

    @NotNull
    private final Country sourceCountry;

    @NotNull
    private final Country destinationCountry;

    @NotNull
    private final Double itemNetPrice;

    @NotNull
    private final Float itemTaxParentage;

    @NotNull
    private final Double totalItemPrice;

    @NotNull
    private final Float distance;
}
