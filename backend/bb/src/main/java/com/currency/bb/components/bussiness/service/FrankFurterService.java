package com.currency.bb.components.bussiness.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.currency.bb.common.CurrencyConstants;
import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;
import com.currency.bb.components.integration.FrankFurterClient;

@Service
public class FrankFurterService {
    
    private final FrankFurterClient client;
    public FrankFurterService(
        FrankFurterClient client
    ){
        this.client = client;
    }

    public FrankfurterApiDTO getLatestRates(String base) throws Exception{
        if (!checkBase(base)) 
            throw new IllegalArgumentException("The base is mistyped or empty!");

        try{
            return this.client.getLatestRates(base);
        }catch(Exception e){
            throw new IllegalArgumentException("The base currency is not defined!");
        }
    }


    public FrankfurterApiDTO getHistoricalRatesWithBase(String base, String date)  throws Exception{
        if (!checkBase(base)) 
            throw new IllegalArgumentException("The base is mistyped or empty!");

        try {
            return this.client.getHistoricalRatesWithBase(base, date);
        } catch (Exception e) {
            // Only unexpected exceptions reach here
            throw new IllegalArgumentException("The base currency or date is not defined!", e);
        }
    }

    public HashMap<String, String> getCurrencyWithExplanations() throws Exception{
        try {
            return this.client.getCurrencyWithExplanations();
        } catch (Exception e) {
            // Only unexpected exceptions reach here
            throw new Exception();
        }
    }

    public FrankfurterApiDTO getHistoricalRatesWithBaseWithLimitedTarget(String base, String date, String target)  throws Exception{
        if (!checkBase(base) && !checkBase(target)) 
            throw new IllegalArgumentException("The base-target is mistyped or empty!");

        try {
            return this.client.getHistoricalRatesWithBaseWithLimitedTarget(base, date, target);
        } catch (Exception e) {
            // Only unexpected exceptions reach here
            throw new IllegalArgumentException("The base currency, date or target is not defined!", e);
        }
    }
    

    public boolean checkBase(String base){
        if(base==null || base.trim().equals("")){
            return false;
        }
        if(CurrencyConstants.CURRENCY_SET.contains(base))
            return true;
        return false;
    }
}
