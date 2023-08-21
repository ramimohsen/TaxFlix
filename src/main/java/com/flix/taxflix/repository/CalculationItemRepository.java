package com.flix.taxflix.repository;


import com.flix.taxflix.entity.CalculationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationItemRepository extends JpaRepository<CalculationItem, Integer> {
}
