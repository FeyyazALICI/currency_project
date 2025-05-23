package com.currency.bb.components.bussiness.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.currency.bb.components.bussiness.dto.CurrencyRatesDTO;
import com.currency.bb.components.bussiness.dtoMappper.CurrencyRatesMapper;
import com.currency.bb.components.bussiness.serviceInterface.CurrencyRatesServiceInterface;
import com.currency.bb.components.dao.entity.CurrencyRates;
import com.currency.bb.components.dao.repo.CurrencyRatesRepo;

import jakarta.transaction.Transactional;

@Service
public class CurrencyRatesService implements CurrencyRatesServiceInterface{
    
    private final CurrencyRatesRepo crudRepo;

    public CurrencyRatesService(
        CurrencyRatesRepo crudRepo
    ){
        this.crudRepo = crudRepo;;
    }

    // CRUD ----------------------------------------------------------    
    @Override
    public List<CurrencyRatesDTO> getAllData() throws Exception{
        try{
            CurrencyRatesMapper mapper = new CurrencyRatesMapper();
            List<CurrencyRates> data = this.crudRepo.findAll();
            List<CurrencyRatesDTO> dataDTO = new ArrayList<>();
            for(CurrencyRates e: data){
                CurrencyRatesDTO itemDTO = new CurrencyRatesDTO();
                itemDTO = mapper.entityToDTO(e);
                dataDTO.add(itemDTO);
            }
            return dataDTO;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CurrencyRatesDTO getDataById( Long id ) throws Exception{
        try{
            CurrencyRatesMapper mapper = new CurrencyRatesMapper();

            Optional<CurrencyRates> data = this.crudRepo.findById(id);
            if(data.isPresent()){
                CurrencyRatesDTO dataDTO = new CurrencyRatesDTO();
                dataDTO = mapper.entityToDTO(data.get());
                return dataDTO;
            }else
                return null;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean insertRow( CurrencyRatesDTO dataDTO ) throws Exception{
        try{
            if(dataDTO.getCurrency()!=null && !dataDTO.getCurrency().trim().equals("") && dataDTO.getRate()!=null && !dataDTO.getRate().trim().equals("")){
                if( this.crudRepo.existsByCurrency(dataDTO.getCurrency()) ){
                    System.out.println("Currency is not Unique!");
                    return false;
                }else{
                    CurrencyRatesMapper mapper = new CurrencyRatesMapper();
                    dataDTO.setId(null);
                    CurrencyRates data = new CurrencyRates();
                    data = mapper.dtoToEntity(dataDTO);
                    this.crudRepo.saveAndFlush(data);
                    return true;
                }
            }
            System.out.println("Nor currency nor rate fields can not be left empty!");
            return false;
        }catch( Exception e ){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean ifRowExists( Long id ) throws Exception{
        try{
            Optional<CurrencyRates> list = this.crudRepo.findById(id);
            if(   list.isPresent()   )
                return true;
            return false;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateRow( CurrencyRatesDTO dataDTO ) throws Exception{
        try{
            CurrencyRatesMapper mapper = new CurrencyRatesMapper();
            CurrencyRates data = new CurrencyRates();
            data = mapper.dtoToEntity(dataDTO);
            if( ifRowExists( data.getId() ) ){
                this.crudRepo.saveAndFlush(data);
                return true;
            }
            System.out.println("Data with given id does not exist!");
            return false;
        }catch( Exception e ){
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    @Transactional
    public boolean deleteRow( Long id ) throws Exception{
        try{
            if( ifRowExists( id ) ){
                this.crudRepo.deleteById( id );
                return true;
            }
            System.out.println("Data with given id does not exist!");
            return false;
        }catch( Exception e ){
            System.out.println("Internal Server Error!");
            return false;
        }
    }
}
