package com.currency_project.def.components.bussiness.dtoMappper;

import java.math.BigDecimal;

import com.currency_project.def.components.bussiness.dto.CurrencyRatesDTO;
import com.currency_project.def.components.dao.entity.CurrencyRates;

public class CurrencyRatesMapper {
    
    public CurrencyRates dtoToEntity( CurrencyRatesDTO dataGiven){
        // attributes
        Long id             = null;

        String currency     = null;
        BigDecimal rate     = null;

        // transformation   ----------------------------------------------------------------
        // id
        if( dataGiven.getId()==null || ( dataGiven.getId().trim().equals("") ) ){
            id = null;
        }else{
            id = Long.parseLong(dataGiven.getId());
        }

        // currency
        if( dataGiven.getCurrency()==null || ( dataGiven.getCurrency().trim().equals("") )){
            currency = null;
        }else{
            currency = dataGiven.getCurrency();
        }

        // rate
        if( dataGiven.getRate()==null ){
            rate = null;
        }else{
            rate = new BigDecimal( dataGiven.getRate() );
        }

        // Entity creation
        CurrencyRates entityToGive = new CurrencyRates();
        entityToGive.setId(id);

        entityToGive.setCurrency(currency);
        entityToGive.setRate(rate);

        return entityToGive;
    }



    public CurrencyRatesDTO entityToDTO( CurrencyRates dataGiven ){
        // attributes
        String id           = null;

        String currency     = null;
        String rate         = null;

        // id
        if(   dataGiven.getId()==null   ){
            id = null;
        }else{
            id = dataGiven.getId().toString();
        }

        // currency
        if(   dataGiven.getCurrency()==null   ){
            currency = null;
        }else{
            currency = dataGiven.getCurrency();
        }

        // rate
        if(   dataGiven.getRate()==null   ){
            rate = null;
        }else{
            rate = dataGiven.getRate().toString();
        }

        // DTO creation
        CurrencyRatesDTO dtoToGive = new CurrencyRatesDTO();
        dtoToGive.setId(id);

        dtoToGive.setCurrency(currency);
        dtoToGive.setRate(rate);

        return dtoToGive;
    }


}
