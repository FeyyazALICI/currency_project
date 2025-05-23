package com.currency.bb.components.bussiness.serviceInterface;

import java.util.List;

import com.currency.bb.components.bussiness.dto.CurrencyRatesDTO;

public interface CurrencyRatesServiceInterface {
    
    // CRUD -------------------------------------------------------
    public List<CurrencyRatesDTO> getAllData() throws Exception;
    public CurrencyRatesDTO getDataById( Long id ) throws Exception;
    public boolean insertRow( CurrencyRatesDTO data ) throws Exception;
    public boolean ifRowExists( Long id ) throws Exception;
    public boolean updateRow( CurrencyRatesDTO data ) throws Exception;
    public boolean deleteRow( Long id ) throws Exception;
    // CRUD -------------------------------------------------------


}