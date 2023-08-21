package com.flix.taxflix.entity;


import com.flix.taxflix.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaxCalculationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country sourceCountry;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country distenationCountry;

    @Column(nullable = false)
    private LocalDateTime effectiveDate;

    @Column(nullable = false)
    private Float shortTaxValueParentage;

    @Column(nullable = false)
    private Float longTaxValueParentage;

    @Column(nullable = false)
    private Float threshold;

    @Column(nullable = false)
    private Integer priority;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
