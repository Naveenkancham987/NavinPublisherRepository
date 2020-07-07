package com.pk.publisher.model;

import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * SucessResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
    date = "2020-06-18T12:14:57.498Z")

public class CustomerResponse {
  @JsonProperty("status")
  private String status = null;

  @JsonProperty("message")
  private String message = null;



  /**
   * Get status
   * 
   * @return status
   **/
  @ApiModelProperty(example = "success", required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }



  /**
   * Get message
   * 
   * @return message
   **/
  @ApiModelProperty(example = "Sucessfully published the customer information", required = true,
      value = "")
  @NotNull
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }



}

