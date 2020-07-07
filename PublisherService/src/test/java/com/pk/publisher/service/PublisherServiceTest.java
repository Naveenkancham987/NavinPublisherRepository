package com.pk.publisher.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.SettableListenableFuture;
import com.pk.publisher.converters.CustomerMaskConverter;
import com.pk.publisher.converters.ResponseConverter;
import com.pk.publisher.model.Address;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.Customer.CustomerStatusEnum;
import com.pk.publisher.model.CustomerResponse;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PublisherServiceTest {

  private static String successStatus = "SUCCESS";

  @Autowired
  private PublisherService publisherService;

  @MockBean
  private CustomerMaskConverter customerMaskConverter;

  @MockBean
  private ResponseConverter responseConverter;

  @MockBean
  private KafkaTemplate<String, Customer> kafkatemplate;

  @Before
  public void init() throws InterruptedException, ExecutionException, TimeoutException {
    String topicName = "test.topic";
    SettableListenableFuture<SendResult<String, Customer>> future = getFutureObject();
    when(kafkatemplate.send(topicName, createCustomer())).thenReturn(future);
  }

  @Test
  public void testPublishCustomerObject() throws Exception {
    when(customerMaskConverter.convert(Mockito.any(Customer.class)))
        .thenReturn(createMaskCustomer());
    when(responseConverter.convert(Mockito.anyString())).thenReturn(createResponse());
    CustomerResponse customerResponse = publisherService.publishCustomerObject(createCustomer());
    assertEquals(successStatus, customerResponse.getStatus());
    assertEquals("Customer Added Successfully", customerResponse.getMessage());
  }


  private CustomerResponse createResponse() {
    CustomerResponse response = new CustomerResponse();
    response.setMessage("Customer Added Successfully");
    response.setStatus(successStatus);
    return response;
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

  private SettableListenableFuture<SendResult<String, Customer>> getFutureObject()
      throws InterruptedException, ExecutionException, TimeoutException {
    SettableListenableFuture<SendResult<String, Customer>> future =
        mock(SettableListenableFuture.class);
    when(future.get(30000, TimeUnit.MILLISECONDS)).thenReturn(null);
    return future;
  }

  private Customer createMaskCustomer() {
    Customer maskCustomer = createCustomer();
    maskCustomer.setCustomerNumber("C00****");
    maskCustomer.setEmail("****n27@gmail.com");
    maskCustomer.setBirthdate("**-**-2019");
    return maskCustomer;
  }
}
