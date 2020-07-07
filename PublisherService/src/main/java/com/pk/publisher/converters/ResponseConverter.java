package com.pk.publisher.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.pk.publisher.model.CustomerResponse;
import com.pk.publisher.utility.PublisherServiceConstants;

/**
 * 
 * @author nkancham
 *
 */
@Component
public class ResponseConverter implements Converter<String, CustomerResponse> {

  /**
   * @return CustomerResponse
   */
  @Override
  public CustomerResponse convert(String message) {
    CustomerResponse successResponse = new CustomerResponse();
    successResponse.setMessage(message);
    successResponse.setStatus(PublisherServiceConstants.SUCCESS);
    return successResponse;

  }
}
