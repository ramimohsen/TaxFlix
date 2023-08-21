package com.flix.taxflix.dto;


import com.flix.taxflix.enums.Country;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationItemRequest {

    @NotNull
    private Country sourceCountry;

    @NotNull
    private Country destinationCountry;

    @NotNull
    private Double totalItemNetPrice;

    @NotNull
    private Float distance;
}
