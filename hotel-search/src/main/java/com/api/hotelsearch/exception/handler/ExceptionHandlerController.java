package com.api.hotelsearch.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.hotelsearch.exception.HotelSearchDoesNotExistException;
import com.api.hotelsearch.exception.MessagesProducerServiceException;
import com.api.hotelsearch.model.ErrorInfo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        List<ObjectError> objectErrors = result.getAllErrors();

        List<String> errorMessages = new ArrayList<>();
        objectErrors.forEach( o -> errorMessages.add(o.getDefaultMessage()));
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        List<String> errorMessages = new ArrayList<>();
        
        if(e.getCause() instanceof InvalidFormatException invalid) {
            errorMessages.add("Date field must be a valid date with dd/MM/yyyy pattern.");
        } else {
            errorMessages.add(e.getMessage());
        }
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MessagesProducerServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorInfo> httpMessageNotReadableException(HttpServletRequest request, MessagesProducerServiceException e) {
        List<String> errorMessages = new ArrayList<>();
        
        errorMessages.add(e.getMessage());
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(HotelSearchDoesNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorInfo> hotelSearchDoesNotExistException(HttpServletRequest request, HotelSearchDoesNotExistException e) {
        List<String> errorMessages = new ArrayList<>();
        
        errorMessages.add(e.getMessage());
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.CONFLICT.value(), request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorInfo> captureAllExceptions(HttpServletRequest request, Exception e) {
        List<String> errorMessages = new ArrayList<>();
        
        errorMessages.add("There was an error in the application.");
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
