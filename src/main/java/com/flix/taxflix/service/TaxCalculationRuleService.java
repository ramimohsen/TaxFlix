package com.flix.taxflix.service;


import com.flix.taxflix.dto.CalculationItemRequest;
import com.flix.taxflix.dto.CalculationItemResponse;
import com.flix.taxflix.dto.CalculationRequestDTO;
import com.flix.taxflix.dto.CalculationResponseDTO;
import com.flix.taxflix.entity.CalculationItem;
import com.flix.taxflix.entity.TaxCalculationRule;
import com.flix.taxflix.repository.CalculationItemRepository;
import com.flix.taxflix.repository.TaxCalculationRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxCalculationRuleService {

    private final TaxCalculationRuleRepository taxCalculationRuleRepository;

    private final CalculationItemRepository calculationItemRepository;

    public CalculationResponseDTO calculate(CalculationRequestDTO calculationRequestDTO) {

        List<CalculationItemResponse> calculationItemResponses = calculationRequestDTO
                .getCalculationItemRequests().stream().map(this::getCalculationItemResponse).collect(Collectors.toList());

        Double totalItemPrice = calculationItemResponses.stream()
                .mapToDouble(CalculationItemResponse::getTotalItemPrice)
                .sum();

        Double totalItemTax = calculationItemResponses.stream()
                .mapToDouble(CalculationItemResponse::getItemTaxParentage)
                .sum();

        Double totalItemNet = calculationItemResponses.stream()
                .mapToDouble(CalculationItemResponse::getItemNetPrice)
                .sum();

        CalculationItem calculationItem = this.calculationItemRepository.save(CalculationItem.builder()
                .totalItemsTaxParentage(totalItemTax)
                .totalNetItemsPrice(totalItemNet).
                totalItemsGrossPrice(totalItemPrice).build());

        return CalculationResponseDTO.builder().calculationItemResponses(calculationItemResponses)
                .totalItemsGrossPrice(totalItemPrice)
                .totalItemsTaxParentage(totalItemTax)
                .totalNetItemsPrice(totalItemNet)
                .calculationId(calculationItem.getId()).build();
    }

    private CalculationItemResponse getCalculationItemResponse(CalculationItemRequest calculationItemRequest) {
        TaxCalculationRule taxCalculationRule =
                this.taxCalculationRuleRepository
                        .findFirsBySourceCountryAndDistenationCountryOrderByPriorityDesc(calculationItemRequest.getSourceCountry(),
                                calculationItemRequest.getDestinationCountry());

        Float taxValue = calculationItemRequest.getDistance().compareTo(taxCalculationRule.getThreshold()) <= 0 ?
                taxCalculationRule.getShortTaxValueParentage() : taxCalculationRule.getLongTaxValueParentage();

        Double totalPrice = calculationItemRequest.getTotalItemNetPrice() +
                (calculationItemRequest.getTotalItemNetPrice() * (taxValue.doubleValue() / 100));

        return CalculationItemResponse.builder()
                .sourceCountry(calculationItemRequest.getSourceCountry())
                .destinationCountry(calculationItemRequest.getDestinationCountry())
                .distance(calculationItemRequest.getDistance())
                .itemNetPrice(calculationItemRequest.getTotalItemNetPrice())
                .itemTaxParentage(taxValue).totalItemPrice(totalPrice).build();
    }

    public CalculationItem getCalculationItem(Integer id) {
        return this.calculationItemRepository.findById(id).orElse(null);
    }
}
