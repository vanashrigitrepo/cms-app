package com.ibm.cms.globalexceptionhandling;

import com.ibm.cms.dto.ComplaintResponseDto;
import com.ibm.cms.exception.IdNotFound;
import com.ibm.cms.exception.KeywordNotFound;
import com.ibm.cms.exception.StatusNotFoundException;
import jakarta.persistence.ElementCollection;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleIdNotFoundExc (IdNotFound ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleKeywordNotFound(KeywordNotFound ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> statusError(StatusNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
