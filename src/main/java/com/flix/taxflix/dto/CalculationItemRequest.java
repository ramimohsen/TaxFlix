package com.flix.taxflix.dto;


import com.flix.taxflix.enums.Country;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CalculationItemRequest {

    @NotNull
    private final Country sourceCountry;

    @NotNull
    private final Country destinationCountry;

    @NotNull
    private final Double totalItemNetPrice;

    @NotNull
    private final Float distance;
}
