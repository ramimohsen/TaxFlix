package com.flix.taxflix.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequestDTO {

    @NotNull
    private List<CalculationItemRequest> calculationItemRequests;
}
