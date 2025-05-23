package com.currency.bb.components.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.bb.components.dao.entity.CurrencyRates;

public interface CurrencyRatesRepo extends JpaRepository<CurrencyRates, Long>{
    
    boolean existsByCurrency(String currency);
}
