package com.ufrn.imd.web2.projeto01.livros.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable{

    private Date date;
    private String message;


    public ExceptionResponse(Date date, String message) {
        this.date = date;
        this.message = message;
    }


    public Date getDate() {
        return date;
    }


    public String getMessage() {
        return message;
    }

    
    
    
}
