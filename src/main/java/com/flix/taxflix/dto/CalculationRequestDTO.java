package com.flix.taxflix.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
public class CalculationRequestDTO {

    @NotNull
    private final List<CalculationItemRequest> calculationItemRequests;
}
