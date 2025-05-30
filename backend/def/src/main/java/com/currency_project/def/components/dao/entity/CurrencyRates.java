package com.currency_project.def.components.dao.entity;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "currency_rates")
public class CurrencyRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency")
    private String currency;
    @Column(name = "rate")
    private BigDecimal rate;

    public CurrencyRates(
        Long id,

        String currency,
        BigDecimal rate
    ){
        this.id = id;

        this.currency   = currency;
        this.rate       = rate;
    }
}
