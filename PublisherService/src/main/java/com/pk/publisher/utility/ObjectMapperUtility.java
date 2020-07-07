package com.pk.publisher.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author nkancham
 *
 */
@Component
public class ObjectMapperUtility {
  private static final Logger log = LoggerFactory.getLogger(ObjectMapperUtility.class);

  private ObjectMapperUtility() {

  }
  
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * 
   * @param object
   * @return String
   */
  public  String returnJsonFromObject(Object object) {
    String response = null;
    try {
      response = objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException exe) {
      log.error("exception occured during json parsing{}", exe.getMessage());
    }
    return response;
  }



}
