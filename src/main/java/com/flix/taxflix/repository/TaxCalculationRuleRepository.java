package com.flix.taxflix.repository;

import com.flix.taxflix.entity.TaxCalculationRule;
import com.flix.taxflix.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaxCalculationRuleRepository extends JpaRepository<TaxCalculationRule,Integer> {

   TaxCalculationRule findFirsBySourceCountryAndDistenationCountryOrderByPriorityDesc(Country source, Country dist);

}
