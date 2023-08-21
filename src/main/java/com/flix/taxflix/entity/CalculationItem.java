package com.flix.taxflix.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private  Double totalNetItemsPrice;

    @Column(nullable = false)
    private  Double totalItemsTaxParentage;

    @Column(nullable = false)
    private  Double totalItemsGrossPrice;
}
