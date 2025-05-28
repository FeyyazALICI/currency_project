package com.currency.bb.components.integration;

import java.util.HashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;

@Component
public class FrankFurterClient {
    
    private final RestTemplate restTemplate;
    public FrankFurterClient(
        RestTemplateBuilder builder
    ){
        this.restTemplate = builder.build();
    }

    String urlLatestApi = "https://api.frankfurter.dev/v1/latest?base=";        // base curency should be added
    public FrankfurterApiDTO getLatestRates(String base) {
        String url = urlLatestApi + base;
        return restTemplate.getForObject(url, FrankfurterApiDTO.class);
    }

    String urlHistoricalRatesWithBase = "https://api.frankfurter.dev/v1/";        // base curency & date should be added  | "date": "1999-01-04" | yyyy.MM.dd
    public FrankfurterApiDTO getHistoricalRatesWithBase( String base, String date ) {
        String url = urlHistoricalRatesWithBase + date + "?base=" + base;
        return restTemplate.getForObject(url, FrankfurterApiDTO.class);
    }

    String currencyWithExplanation = "https://api.frankfurter.dev/v1/currencies";
    public HashMap<String, String> getCurrencyWithExplanations() {
        String url = currencyWithExplanation;
        return restTemplate.getForObject(url, HashMap.class);
    }

    public FrankfurterApiDTO getHistoricalRatesWithBaseWithLimitedTarget( String base, String date, String target ) {
        String url = urlHistoricalRatesWithBase + date + "?base=" + base + "&symbols=" + target;
        return restTemplate.getForObject(url, FrankfurterApiDTO.class);
    }

}

