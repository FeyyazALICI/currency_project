package com.currency_project.def.components.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency_project.def.common.ErrorMessageDerived;
import com.currency_project.def.common.HttpHeaderCreator;
import com.currency_project.def.components.api.controllerInterface.CurrencyRatesControllerInterface;
import com.currency_project.def.components.bussiness.service.CurrencyRatesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.currency_project.def.components.bussiness.dto.CurrencyRatesDTO;
import org.springframework.http.HttpHeaders;

@RequestMapping("/bb")
@RestController
public class CurrencyRatesController implements CurrencyRatesControllerInterface{
    
    private final CurrencyRatesService service;
    private final HttpHeaderCreator httpHeaderCreator;

    public CurrencyRatesController(
        CurrencyRatesService service,
        HttpHeaderCreator httpHeaderCreator
    ){
        this.service                = service;
        this.httpHeaderCreator      = httpHeaderCreator;
    }

    @Override
    @GetMapping()
    public ResponseEntity getAllData(HttpServletRequest request){
        String requestType = "GET";
        try{
            List<CurrencyRatesDTO> data = service.getAllData();
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

    @Override
    @PostMapping("/id")
    public ResponseEntity getDataById(HttpServletRequest request, @RequestBody HashMap<String, String> dataReceived   ){
        String requestType = "POST";
        try{
            Long id = Long.valueOf(dataReceived.get("id"));
            CurrencyRatesDTO data = service.getDataById(   id   );
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

    @Override
    @PostMapping()
    public ResponseEntity insertRow(   HttpServletRequest request, @Valid @RequestBody CurrencyRatesDTO dataDTO   ){
        ErrorMessageDerived emd = new ErrorMessageDerived();
        String requestType = "POST";
        try{
            if( service.insertRow(dataDTO) ){
                HttpHeaders responseHeader = this.httpHeaderCreator.okResponseHeader(request, requestType);
                return new ResponseEntity<>(dataDTO, responseHeader, HttpStatus.OK);
            }
            HttpHeaders responseHeader = this.httpHeaderCreator.conflictResponseHeader(request, requestType);
            return new ResponseEntity<>( emd.conflictError(), responseHeader, HttpStatus.CONFLICT);
        }catch( Exception e ){
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping()
    public ResponseEntity updateRow(   HttpServletRequest request, @Valid @RequestBody CurrencyRatesDTO dataDTO   ){
        ErrorMessageDerived emd = new ErrorMessageDerived();
        String requestType = "PUT";
        try{
            if( service.updateRow(dataDTO) ){
                HttpHeaders responseHeader = this.httpHeaderCreator.okResponseHeader(request, requestType);
                return new ResponseEntity<>(dataDTO, responseHeader, HttpStatus.OK);
            }
            HttpHeaders responseHeader = this.httpHeaderCreator.notFoundResponseHeader(request, requestType);
            return new ResponseEntity<>( emd.notFoundError(dataDTO.getId().toString()), responseHeader, HttpStatus.NOT_FOUND);
        }catch( Exception e ){
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteRow(   HttpServletRequest request, @RequestBody HashMap<String, String> dataReceived   ){
        Long id = Long.valueOf( dataReceived.get("id") );
        String requestType = "DELETE";
        try{
            if( service.deleteRow(id) ){
                HttpHeaders responseHeader = this.httpHeaderCreator.okResponseHeader(request, requestType);
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("message", "Delete Operation is successful!");
                return new ResponseEntity<>(responseBody, responseHeader, HttpStatus.OK);
            }
            ErrorMessageDerived emd = new ErrorMessageDerived();
            HttpHeaders responseHeader = this.httpHeaderCreator.conflictResponseHeader(request, requestType);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", emd.notFoundErrorById(id));
            return new ResponseEntity<>( responseBody, responseHeader, HttpStatus.CONFLICT);
        }catch( Exception e ){
            HttpHeaders responseHeader = this.httpHeaderCreator.internalServerErrorResponseHeader(request, requestType);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "Internal Server Error");
            return new ResponseEntity<>(responseBody, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
