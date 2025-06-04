package com.currency.bb.components.api.controller.excel;

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
import com.currency.bb.components.bussiness.service.excel.FrankfurtherDerivedExcelService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/frank/excel/d")
@RestController
public class FrankFurterDerivedControllerExcel{
    
    private final FrankFurterDerivedService service;
    private final FrankfurtherDerivedExcelService excelService;
    private final HttpHeaderCreator httpHeaderCreator;

    public FrankFurterDerivedControllerExcel(
        FrankFurterDerivedService service,
        HttpHeaderCreator httpHeaderCreator,
        FrankfurtherDerivedExcelService excelService
    ){
        this.service = service;
        this.httpHeaderCreator = httpHeaderCreator;
        this.excelService = excelService;
    }

    // payload: {"currency": "USD", "dateOnSet": "1999-01-01", "dateEnd": "1999-02-01", "target": "EUR"}
    @PostMapping("/ts") // ts -> time specific
    public ResponseEntity<byte[]> timeLineOfSpecificCurrency(HttpServletRequest request, @RequestBody HashMap<String, String> payload   ) throws Exception{

        String requestType = "POST";
        try{
            String base         = payload.get("currency");
            String dateOnSet    = payload.get("dateOnSet");     // "1999-01-04"
            String dateEnd      = payload.get("dateEnd");       // "1999-01-04"
            String target       = payload.get("target");
            ArrayList<FrankfurterApiDTO> data = service.timeLineOfSpecificCurrency(   base, dateOnSet, dateEnd, target   );
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


}
