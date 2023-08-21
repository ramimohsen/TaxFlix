package com.flix.taxflix.controller;


import com.flix.taxflix.dto.CalculationRequestDTO;
import com.flix.taxflix.dto.CalculationResponseDTO;
import com.flix.taxflix.entity.CalculationItem;
import com.flix.taxflix.service.TaxCalculationRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/tax")
public class TaxCalculationController {

    private final TaxCalculationRuleService taxCalculationRuleService;


    @PostMapping("/calculate")
    public CalculationResponseDTO calculateTaxes(@RequestBody CalculationRequestDTO calculationRequestDTO) {
        return this.taxCalculationRuleService.calculate(calculationRequestDTO);
    }

    @GetMapping("/item/{id}")
    public CalculationItem getItem(@PathVariable("id") Integer id) {
        return this.taxCalculationRuleService.getCalculationItem(id);
    }

}
