package com.ibm.cms.exception;

public class StatusNotFoundException extends RuntimeException {

    public StatusNotFoundException(String status){
        super("Entered Status is Incorrect "+ status);
    }

}
