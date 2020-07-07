package com.pk.publisher.exception;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pk.publisher.model.ErrorResponse;
import com.pk.publisher.utility.ObjectMapperUtility;
import com.pk.publisher.utility.PublisherServiceConstants;

/**
 * 
 * @author navi
 *
 */
@RestControllerAdvice
public class PublisherControllerAdvice {

  @Autowired
  private ObjectMapperUtility objectMapperUtility;



  /**
   * 
   * @param methodArgumentNotValidException
   * @return ResponseEntity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentException(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpServletRequest servletRequest) {
    List<FieldError> listFieldErrors =
        methodArgumentNotValidException.getBindingResult().getFieldErrors();
    Map<String, TreeSet<String>> fieldValidationError = new TreeMap<>();
    for (FieldError fieldError : listFieldErrors) {
      if (fieldValidationError.containsKey(fieldError.getField())) {
        TreeSet<String> error = fieldValidationError.get(fieldError.getField());
        error.add(fieldError.getDefaultMessage());
        fieldValidationError.put(fieldError.getField(), error);
      } else {
        TreeSet<String> error = new TreeSet<>();
        error.add(fieldError.getDefaultMessage());
        fieldValidationError.put(fieldError.getField(), error);
      }
    }

    ErrorResponse error = new ErrorResponse();
    error.setErrorType(MethodArgumentNotValidException.class.getSimpleName());
    error.setMessage("Input CustomerRequest field level validations failed"
        + objectMapperUtility.returnJsonFromObject(fieldValidationError));
    error.setStatus(PublisherServiceConstants.ERROR);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  /**
   * 
   * @param GeneralInternalException
   * @return ResponseEntity
   */
  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ErrorResponse> handleGeneralInternalException(GeneralException exception,
      HttpServletRequest servletRequest) {
    ErrorResponse error = new ErrorResponse();
    error.setErrorType(GeneralException.class.getSimpleName());
    error.setMessage("Internal Exception Occcured " + exception.getMessage());
    error.setStatus(PublisherServiceConstants.ERROR);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 
   * @param AuthenticationException
   * @return ResponseEntity
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleException(AuthenticationException exception,
      HttpServletRequest servletRequest) {
    ErrorResponse error = new ErrorResponse();
    error.setErrorType(AuthenticationException.class.getSimpleName());
    error.setMessage("Token error. " + exception.getMessage());
    error.setStatus(PublisherServiceConstants.ERROR);
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  /**
   * 
   * @param NoHandlerFoundException
   * @return ResponseEntity
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
      NoHandlerFoundException exception, HttpServletRequest servletRequest) {
    ErrorResponse error = new ErrorResponse();
    error.setErrorType(NoHandlerFoundException.class.getSimpleName());
    error.setMessage("Requested resource not found. " + exception.getMessage());
    error.setStatus(PublisherServiceConstants.ERROR);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  /**
   * 
   * @param ServletRequestBindingException
   * @return ResponseEntity
   */
  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<ErrorResponse> handleMandatoryHeadersMissingException(
      ServletRequestBindingException exception, HttpServletRequest servletRequest) {
    ErrorResponse error = new ErrorResponse();
    error.setErrorType(ServletRequestBindingException.class.getSimpleName());
    error.setMessage("Required headers are missing. " + exception.getMessage());
    error.setStatus(PublisherServiceConstants.ERROR);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
