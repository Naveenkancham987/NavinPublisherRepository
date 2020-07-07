package com.pk.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.pk.publisher.converters.CustomerMaskConverter;
import com.pk.publisher.converters.ResponseConverter;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.CustomerResponse;
import com.pk.publisher.utility.ObjectMapperUtility;
import com.pk.publisher.utility.PublisherServiceConstants;

/**
 * 
 * @author navi
 *
 */

@Service
public class PublisherService {

  private static final Logger log = LoggerFactory.getLogger(PublisherService.class);

  @Autowired
  private KafkaTemplate<String, Customer> kafkatemplate;

  @Autowired
  private CustomerMaskConverter customerMaskConverter;

  @Autowired
  private ResponseConverter responseConverter;

  @Autowired
  private ObjectMapperUtility objectMapper;

  /**
   * 
   * @param customer
   * @return ResponseEntity
   */
  public CustomerResponse publishCustomerObject(Customer customerRequest) {
    Customer customerMasked = customerMaskConverter.convert(customerRequest);
    String customerJson = objectMapper.returnJsonFromObject(customerMasked);
    log.info("Customer Incoming object before publishing {}", customerJson);
    kafkatemplate.send(PublisherServiceConstants.TOPIC, customerMasked);
    CustomerResponse response =
        responseConverter.convert(PublisherServiceConstants.SUCCESSFULLY_PUBLISHED);
    log.info("Customer response object after publishing customer object{}", response);
    return response;
  }
}
