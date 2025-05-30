package com.currency_project.def.components.bussiness.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyRatesDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("rate")
    private String rate;

    public CurrencyRatesDTO(
        String id,

        String currency,
        String rate
    ){
        this.id = id;

        this.currency   = currency;
        this.rate       = rate;
    }
}
