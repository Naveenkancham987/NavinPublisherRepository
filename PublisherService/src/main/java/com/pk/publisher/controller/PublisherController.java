package com.pk.publisher.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.CustomerResponse;
import com.pk.publisher.service.PublisherService;


/**
 * 
 * @author navi
 *
 */
@RestController
public class PublisherController {

  private static final Logger log = LoggerFactory.getLogger(PublisherController.class);

  @Autowired
  private PublisherService publisherService;

  /**
   * 
   * @param customer
   * @return ResponseEntity with success message
   */
  @PostMapping(value = "/publish", consumes = "application/json", produces = "application/json")
  public ResponseEntity<CustomerResponse> publishCustomerInformation(
      @RequestHeader(value = "Authorization", required = true) String authorizaton,
      @RequestHeader(value = "Activity-Id", required = true) String activityId,
      @RequestHeader(value = "Application-id", required = true) String applicationId,
      @Valid @RequestBody Customer customer) {
    log.info("PublisherController {}", " publishCustomerInformation method begins");
    CustomerResponse response = publisherService.publishCustomerObject(customer);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
