package com.currency_project.def.components.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency_project.def.components.dao.entity.CurrencyRates;

public interface CurrencyRatesRepo extends JpaRepository<CurrencyRates, Long>{
    
    boolean existsByCurrency(String currency);
}
