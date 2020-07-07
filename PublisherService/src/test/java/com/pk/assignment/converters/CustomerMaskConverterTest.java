package com.pk.assignment.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import com.pk.publisher.converters.CustomerMaskConverter;
import com.pk.publisher.model.Address;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.Customer.CustomerStatusEnum;

public class CustomerMaskConverterTest {

  private CustomerMaskConverter converterMaskConverter;

  @Before
  public void setup() {
    converterMaskConverter = new CustomerMaskConverter();
  }

  @Test
  public void testConvertWhenPassingValidCustomerShouldApplyMasking() {
    Customer result = converterMaskConverter.convert(createCustomer());
    assertNotNull(result);
    assertEquals("C00****", result.getCustomerNumber());
    assertEquals("****n27@gmail.com", result.getEmail());
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("2-12-2012");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C000001");
    customer.setEmail("navin27@gmail.com");
    customer.setCustomerStatus(CustomerStatusEnum.RESTORED);
    customer.setFirstName("Foo");
    customer.setLastName("boo");
    customer.setMobileNumber("1234567890");
    return customer;

  }

  private Address createAddress() {
    Address address = new Address();
    address.setAddressLine1("AddressLine1");
    address.setAddressLine2("AddressLine2");
    address.setPostalCode("12345");
    address.setStreet("Street");
    return address;
  }
}
