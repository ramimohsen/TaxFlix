package com.flix.taxflix.repository;

import com.flix.taxflix.entity.TaxCalculationRule;
import com.flix.taxflix.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaxCalculationRuleRepository extends JpaRepository<TaxCalculationRule,Integer> {

   List<TaxCalculationRule> findTaxCalculationRuleBySourceCountryAndDistenationCountryOrderByPriorityDesc(Country source, Country dist);

}
