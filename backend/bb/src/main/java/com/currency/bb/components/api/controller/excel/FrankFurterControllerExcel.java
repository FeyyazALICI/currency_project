package com.currency.bb.components.api.controller.excel;

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
import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;
import com.currency.bb.components.bussiness.service.FrankFurterService;
import com.currency.bb.components.bussiness.service.excel.FrankfurtherExcelService;
import com.currency.bb.components.bussiness.service.excel.FrankfurtherExcelServiceCurrencyDescription;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/frank/excel")
@RestController
public class FrankFurterControllerExcel{
    
    private final FrankFurterService service;
    private final FrankfurtherExcelService excelService;
    private final FrankfurtherExcelServiceCurrencyDescription excelService2;
    private final HttpHeaderCreator httpHeaderCreator;

    public FrankFurterControllerExcel(
        FrankFurterService service,
        HttpHeaderCreator httpHeaderCreator,
        FrankfurtherExcelService excelService,
        FrankfurtherExcelServiceCurrencyDescription excelService2
    ){
        this.service = service;
        this.httpHeaderCreator = httpHeaderCreator;
        this.excelService = excelService;
        this.excelService2 = excelService2;
    }

    // payload: {"currency": "USD"}
    @PostMapping("/latest_with_base")
    public ResponseEntity<byte[]> getLatestCurrencyRatesByBaseCurrencyExcel(HttpServletRequest request, @RequestBody HashMap<String, String> payload   ) throws Exception{

        String requestType = "POST";
        try{
            String currency = payload.get("currency");
            FrankfurterApiDTO data = service.getLatestRates(   currency   );
            if(   data!=null   ){
                ResponseEntity<byte[]> dataExcel = this.excelService.exportAllDataToExcel(data);
                return dataExcel;
            }else{
                HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
                return new ResponseEntity<>(null, responseHeader, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            e.printStackTrace();
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // payload: {"currency": "USD", "date": "1999-01-04"}
    @PostMapping("/historical_with_base")
    public ResponseEntity<byte[]> getHistoricalRatesWithBase(HttpServletRequest request, @RequestBody HashMap<String, String> payload) throws Exception{

        String requestType = "POST";
        try{
            String base = payload.get("currency");
            String date = payload.get("date"); // "1999-01-04"
            FrankfurterApiDTO data = service.getHistoricalRatesWithBase(base, date);
            if(   data!=null   ){
                ResponseEntity<byte[]> dataExcel = this.excelService.exportAllDataToExcel(data);
                return dataExcel;
            }else{
                HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
                return new ResponseEntity<>(null, responseHeader, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            e.printStackTrace();
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // payload: {"currency": "USD", "date": "1999-01-04", "target": "EUR"}
    @PostMapping("/historical_with_base_with_limited_target")
    public ResponseEntity<byte[]> getHistoricalRatesWithBaseWithLimitedTarget(HttpServletRequest request, @RequestBody HashMap<String, String> payload) throws Exception{

        String requestType = "POST";
        try{
            String base = payload.get("currency");
            String date = payload.get("date"); // "1999-01-04"
            String target = payload.get("target");
            FrankfurterApiDTO data = service.getHistoricalRatesWithBaseWithLimitedTarget(base, date, target);
            if(   data!=null   ){
                ResponseEntity<byte[]> dataExcel = this.excelService.exportAllDataToExcel(data);
                return dataExcel;
            }else{
                HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
                return new ResponseEntity<>(null, responseHeader, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            e.printStackTrace();
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/currencies")
    public ResponseEntity<byte[]> getCurrencyWithExplanations( HttpServletRequest request ) throws Exception{

        String requestType = "GET";
        try{
            HashMap<String, String> data = service.getCurrencyWithExplanations();
            if(   data!=null   ){
                ResponseEntity<byte[]> dataExcel = this.excelService2.exportAllDataToExcel(data);
                return dataExcel;
            }else{
                HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
                return new ResponseEntity<>(null, responseHeader, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            e.printStackTrace();
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
 