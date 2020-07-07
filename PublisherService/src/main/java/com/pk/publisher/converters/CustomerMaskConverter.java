package com.pk.publisher.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.pk.publisher.model.Customer;
import com.pk.publisher.utility.PublisherServiceConstants;

/**
 * 
 * @author nkancham
 *
 */
@Component
public class CustomerMaskConverter implements Converter<Customer, Customer> {
  /**
   * 
   * @param customerRequest
   * @return masked Customer object
   */
  public Customer convert(Customer inputCustomerRequest) {
    Customer customerRequest = new Customer();
    customerRequest.setCustomerNumber(((String) inputCustomerRequest.getCustomerNumber())
        .replaceAll(PublisherServiceConstants.NUMBER_MASK, PublisherServiceConstants.PATTERN));
    customerRequest.setFirstName(inputCustomerRequest.getFirstName());
    customerRequest.setLastName(inputCustomerRequest.getLastName());
    customerRequest.setBirthdate(inputCustomerRequest.getBirthdate()
        .replaceAll(PublisherServiceConstants.DATE_MASK, PublisherServiceConstants.DATE_PATTERN));
    customerRequest.setCountry(inputCustomerRequest.getCountry());
    customerRequest.setCountryCode(inputCustomerRequest.getCountryCode());
    customerRequest.setMobileNumber(inputCustomerRequest.getMobileNumber());
    customerRequest.setEmail(inputCustomerRequest.getEmail()
        .replaceAll(PublisherServiceConstants.EMAIL_MASK, PublisherServiceConstants.PATTERN));
    customerRequest.setCustomerStatus(inputCustomerRequest.getCustomerStatus());
    customerRequest.setAddress(inputCustomerRequest.getAddress());
    return customerRequest;

  }
}
