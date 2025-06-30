package com.ibm.cms.exception;

public class IdNotFound extends RuntimeException{
   public IdNotFound(Long compaintId){
       super("Compliant not Found with Id: "+compaintId);
   }
}
