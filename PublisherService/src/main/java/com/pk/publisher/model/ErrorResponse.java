package com.pk.publisher.model;

import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * ErrorResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
    date = "2020-06-18T12:14:57.498Z")

public class ErrorResponse {
  @JsonProperty("status")
  private String status = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("error_type")
  private String errorType = null;

  public ErrorResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * 
   * @return status
   **/
  @ApiModelProperty(example = "Error", required = true, value = "")
  @NotNull
  public String getStatus() {
    return status;
  }

  public void setStatus(String badRequest) {
    this.status = badRequest;
  }

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * 
   * @return message
   **/
  @ApiModelProperty(example = "Error occcured during subscribing", required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorResponse errorType(String errorType) {
    this.errorType = errorType;
    return this;
  }

  /**
   * Get errorType
   * 
   * @return errorType
   **/
  @ApiModelProperty(example = "InvalidRequestException", value = "")
  public Object getErrorType() {
    return errorType;
  }

  public void setErrorType(String object) {
    this.errorType = object;
  }

}

