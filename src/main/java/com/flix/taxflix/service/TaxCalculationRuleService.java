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

import java.util.Comparator;
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

        Result result = getTotalSum(calculationItemResponses);

        CalculationItem calculationItem = this.calculationItemRepository.save(CalculationItem.builder()
                .totalItemsTaxParentage(result.totalItemTax)
                .totalNetItemsPrice(result.totalItemNet).
                totalItemsGrossPrice(result.totalItemTax).build());

        return CalculationResponseDTO.builder().calculationItemResponses(calculationItemResponses)
                .totalItemsGrossPrice(result.totalItemPrice)
                .TotalItemsTaxParentage(result.totalItemTax)
                .totalNetItemsPrice(result.totalItemNet)
                .calculationId(calculationItem.getId()).build();
    }

    private static Result getTotalSum(List<CalculationItemResponse> calculationItemResponses) {
        Double totalItemPrice = calculationItemResponses.stream()
                .map(CalculationItemResponse::getTotalItemPrice).mapToDouble(x -> x).sum();

        Double totalItemTax = calculationItemResponses.stream()
                .map(CalculationItemResponse::getItemTaxParentage).mapToDouble(x -> x).sum();

        Double totalItemNet = calculationItemResponses.stream()
                .map(CalculationItemResponse::getItemNetPrice).mapToDouble(x -> x).sum();
        return new Result(totalItemPrice, totalItemTax, totalItemNet);
    }

    private static class Result {
        public final Double totalItemPrice;
        public final Double totalItemTax;
        public final Double totalItemNet;

        public Result(Double totalItemPrice, Double totalItemTax, Double totalItemNet) {
            this.totalItemPrice = totalItemPrice;
            this.totalItemTax = totalItemTax;
            this.totalItemNet = totalItemNet;
        }
    }

    private CalculationItemResponse getCalculationItemResponse(CalculationItemRequest calculationItemRequest) {
        List<TaxCalculationRule> taxCalculationRuleList =
                this.taxCalculationRuleRepository
                        .findTaxCalculationRuleBySourceCountryAndDistenationCountryOrderByPriorityDesc(calculationItemRequest.getSourceCountry(),
                                calculationItemRequest.getDestinationCountry());

        TaxCalculationRule taxCalculationRule =
                taxCalculationRuleList
                        .stream().max(Comparator.comparing(TaxCalculationRule::getPriority)).get();

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
