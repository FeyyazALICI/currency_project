package com.currency.bb.components.api.controller;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.bb.common.HttpHeaderCreator;
import com.currency.bb.components.api.controllerInterface.FrankFurterControllerInterface;
import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;
import com.currency.bb.components.bussiness.service.FrankFurterService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/frank")
@RestController
public class FrankFurterController implements FrankFurterControllerInterface{
    
    private final FrankFurterService service;
    private final HttpHeaderCreator httpHeaderCreator;

    public FrankFurterController(
        FrankFurterService service,
        HttpHeaderCreator httpHeaderCreator
    ){
        this.service = service;
        this.httpHeaderCreator = httpHeaderCreator;
    }

    // payload: {"currency": "USD"}
    @PostMapping("/latest_with_base")
    @Override
    public ResponseEntity getLatestCurrencyRatesByBaseCurrency(HttpServletRequest request, @RequestBody HashMap<String, String> payload   ) throws Exception{
        String requestType = "POST";
        try{
            String currency = payload.get("currency");
            FrankfurterApiDTO data = service.getLatestRates(   currency   );
            if(   data!=null   ){
                HttpHeaders responseHeader = this.httpHeaderCreator.okResponseHeader(request, requestType);
                return new ResponseEntity<>(data, responseHeader, HttpStatus.OK);
            }else{
                HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
                return new ResponseEntity<>(null, responseHeader, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // payload: {"currency": "USD", "date": "1999-01-04"}
    @PostMapping("/historical_with_base")
    @Override
    public ResponseEntity<FrankfurterApiDTO> getHistoricalRatesWithBase(@RequestBody HashMap<String, String> payload) throws Exception{
        String base = payload.get("currency");
        String date = payload.get("date"); // "1999-01-04"
        FrankfurterApiDTO result = service.getHistoricalRatesWithBase(base, date);
        return ResponseEntity.ok(result);
    }


    // payload: {"currency": "USD", "date": "1999-01-04", "target": "EUR"}
    @PostMapping("/historical_with_base_with_limited_target")
    @Override
    public ResponseEntity<FrankfurterApiDTO> getHistoricalRatesWithBaseWithLimitedTarget(@RequestBody HashMap<String, String> payload) throws Exception{
        String base = payload.get("currency");
        String date = payload.get("date"); // "1999-01-04"
        String target = payload.get("target");
        FrankfurterApiDTO result = service.getHistoricalRatesWithBaseWithLimitedTarget(base, date, target);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/currencies")
    @Override
    public ResponseEntity<HashMap<String, String>> getCurrencyWithExplanations() throws Exception{
        HashMap<String, String> result = service.getCurrencyWithExplanations();
        return ResponseEntity.ok(result);
    }
    
}
 