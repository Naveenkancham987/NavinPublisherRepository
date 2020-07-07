package com.pk.publisher.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.pk.publisher.model.Address;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.Customer.CustomerStatusEnum;
import com.pk.publisher.model.CustomerResponse;
import com.pk.publisher.service.PublisherService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PublishControllerTest {

  @Autowired
  private PublisherController publisherController;
  
  @MockBean
  private PublisherService publisherService;
  
  
  @Test
  public void testPublishCustomerInformation() {
    when(publisherService.publishCustomerObject(Mockito.any())).thenReturn(createResponse());
    ResponseEntity<CustomerResponse> response= publisherController.publishCustomerInformation("Authorization", "Act123", "App123", createCustomer());
   assertEquals(HttpStatus.OK,response.getStatusCode());
  }
  
  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("02-12-2019");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C000001");
    customer.setEmail("navin27@gmail.com");
    customer.setCustomerStatus(CustomerStatusEnum.RESTORED);
    customer.setFirstName("Foofoofoof");
    customer.setLastName("boobooboob");
    customer.setMobileNumber("9234567890");
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
  
  private CustomerResponse createResponse() {
    CustomerResponse response = new CustomerResponse();
    response.setMessage("Customer Added Successfully");
    response.setStatus("OK");
    return response;
  }
}
