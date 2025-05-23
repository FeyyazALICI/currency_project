package com.currency.bb.common;

public class ErrorMessageDerived {
    
    public String notFoundError( String id ){
        StringBuilder stb = new StringBuilder();
        stb.append("The row serached does not exists with given id of ");
        stb.append( id );
        return stb.toString();
    }

    public String notFoundErrorById( Long id ){
        StringBuilder stb = new StringBuilder();
        stb.append("The row does not exists with given id of ");
        stb.append(id);
        return stb.toString();
    }

    public String conflictError(){
        return "The attribute is in use!";
    }
    
}
