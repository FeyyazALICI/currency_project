package com.currency.bb.components.api.controllerInterface;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface FrankFurterControllerInterface {
    
    // CRUD -------------------------------------------------------
    public ResponseEntity getLatestCurrencyRatesByBaseCurrency(HttpServletRequest request, @RequestBody HashMap<String, String> payload   ) throws Exception;
    public ResponseEntity<FrankfurterApiDTO> getHistoricalRatesWithBase(@RequestBody HashMap<String, String> payload) throws Exception;
    public ResponseEntity<FrankfurterApiDTO> getHistoricalRatesWithBaseWithLimitedTarget(@RequestBody HashMap<String, String> payload) throws Exception;
    public ResponseEntity<HashMap<String, String>> getCurrencyWithExplanations() throws Exception;
    // CRUD -------------------------------------------------------


}