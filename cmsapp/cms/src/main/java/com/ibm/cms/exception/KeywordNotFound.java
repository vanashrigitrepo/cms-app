package com.ibm.cms.exception;

public class KeywordNotFound extends RuntimeException{
    public KeywordNotFound(String keyword){
        super("Not Content replated to keyword: "+keyword);
    }
}
