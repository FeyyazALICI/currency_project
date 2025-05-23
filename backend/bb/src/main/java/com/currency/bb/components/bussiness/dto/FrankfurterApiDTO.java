package com.currency.bb.components.bussiness.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FrankfurterApiDTO {
    
  @JsonProperty("amount")
  private String amount;

  @JsonProperty("base")
  private String base;

  @JsonProperty("date")
  private String date;

  @JsonProperty("rates")
  private HashMap<String, String> rates;

  public FrankfurterApiDTO(
    String amount,
    String base,
    String date,
    HashMap<String, String> rates
  ){
    this.amount   = amount;
    this.base     = base;
    this.date     = date;
    this.rates    = rates;
  }
}


