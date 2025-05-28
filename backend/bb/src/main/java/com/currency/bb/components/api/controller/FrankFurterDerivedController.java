package com.currency.bb.components.api.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.bb.common.HttpHeaderCreator;
import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;
import com.currency.bb.components.bussiness.service.FrankFurterDerivedService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/frank/d")     // d -> derived
@RestController
public class FrankFurterDerivedController {
    
    private final FrankFurterDerivedService service;
    private HttpHeaderCreator httpHeaderCreator;

    public FrankFurterDerivedController(
        FrankFurterDerivedService service,
        HttpHeaderCreator httpHeaderCreator
    ){
        this.service = service;
        this.httpHeaderCreator = httpHeaderCreator;
    }

    // payload: {"currency": "USD", "dateOnSet": "1999-01-01", "dateEnd": "1999-02-01", "target": "EUR"}
    @PostMapping("/ts") // ts -> time_spesific
    public ResponseEntity getLatestCurrencyRatesByBaseCurrency(HttpServletRequest request, @RequestBody HashMap<String, String> payload   ) throws Exception{
        String requestType = "POST";
        try{
            String base         = payload.get("currency");
            String dateOnSet    = payload.get("dateOnSet"); // "1999-01-04"
            String dateEnd      = payload.get("dateEnd"); // "1999-01-04"
            String target       = payload.get("target");
            ArrayList<FrankfurterApiDTO> data = service.timeLineOfSpecificCurrency(   base, dateOnSet, dateEnd, target   );
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

    

}
