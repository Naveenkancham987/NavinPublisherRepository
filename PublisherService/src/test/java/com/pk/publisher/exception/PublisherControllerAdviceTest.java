package com.pk.publisher.exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.SettableListenableFuture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.publisher.converters.CustomerMaskConverter;
import com.pk.publisher.converters.ResponseConverter;
import com.pk.publisher.model.Address;
import com.pk.publisher.model.Customer;
import com.pk.publisher.model.Customer.CustomerStatusEnum;
import com.pk.publisher.model.CustomerResponse;
import com.pk.publisher.model.ErrorResponse;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PublisherControllerAdviceTest {

  private static String successStatus = "SUCCESS";
  private static String errorStatus = "ERROR";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

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
  public void testPublishCustomerWhenInvalidEmailThenReturnErrorResponse() throws Exception {
    Customer customer = createCustomer().email("abcd@d@gamil.com");
    String mockCustomer = objectMapper.writeValueAsString(customer);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/publish")
        .header("Activity-Id", "123").header("Authorization", getToken())
        .header("Application-Id", "01234").accept(MediaType.APPLICATION_JSON).content(mockCustomer)
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();
    ErrorResponse errorResponse =
        objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
    assertEquals(errorStatus, errorResponse.getStatus());
    assertEquals("MethodArgumentNotValidException", errorResponse.getErrorType());
  }


  @Test
  public void testPublishCustomerWhenhandleNoHandlerFoundExceptionThenReturnErrorResponse()
      throws Exception {
    Customer customer = createCustomer().email("abcd@d@gamil.com");
    String mockCustomer = objectMapper.writeValueAsString(customer);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/publish1")
        .header("Activity-Id", "123").header("Authorization", getToken())
        .header("Application-Id", "01234").accept(MediaType.APPLICATION_JSON).content(mockCustomer)
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();
    ErrorResponse errorResponse =
        objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
    assertEquals(errorStatus, errorResponse.getStatus());
    assertEquals("NoHandlerFoundException", errorResponse.getErrorType());
  }

  @Test
  public void testPublishCustomerWhenHandleMandatoryHeadersMissingExceptionThenReturnErrorResponse()
      throws Exception {
    Customer customer = createCustomer().email("abcd@d@gamil.com");
    String mockCustomer = objectMapper.writeValueAsString(customer);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/publish").header("Authorization", getToken())
            .header("Application-Id", "01234").accept(MediaType.APPLICATION_JSON)
            .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();
    ErrorResponse errorResponse =
        objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
    assertEquals(errorStatus, errorResponse.getStatus());
    assertEquals("ServletRequestBindingException", errorResponse.getErrorType());
  }



  @Test
  public void testPublishCustomerWhenValidCustomerSendAndReturnSuccessResponse() throws Exception {
    String mockCustomer = objectMapper.writeValueAsString(createCustomer());
    when(customerMaskConverter.convert(Mockito.any(Customer.class)))
        .thenReturn(createMaskCustomer());
    when(responseConverter.convert(Mockito.anyString())).thenReturn(createResponse());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/publish")
        .header("Authorization", getToken()).header("Activity-Id", "123")
        .header("Application-Id", "1908").accept(MediaType.APPLICATION_JSON).content(mockCustomer)
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();
    CustomerResponse successResponse =
        objectMapper.readValue(response.getContentAsString(), CustomerResponse.class);
    assertEquals(successStatus, successResponse.getStatus());
    assertEquals("Customer Added Successfully", successResponse.getMessage());
  }

  private CustomerResponse createResponse() {
    CustomerResponse response = new CustomerResponse();
    response.setMessage("Customer Added Successfully");
    response.setStatus(successStatus);
    return response;
  }

  private Customer createMaskCustomer() {
    Customer maskCustomer = createCustomer();
    maskCustomer.setCustomerNumber("C00****");
    maskCustomer.setEmail("****n27@gmail.com");
    maskCustomer.setBirthdate("**-**-2019");
    return maskCustomer;
  }

  private String obtainAccessToken(String username, String password) throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "password");
    params.add("username", username);
    params.add("password", password);
    ResultActions result = mockMvc
        .perform(post("/oauth/token").params(params).with(httpBasic("my-trusted-client", "secret"))
            .accept("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));
    String resultString = result.andReturn().getResponse().getContentAsString();
    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("access_token").toString();
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

  private SettableListenableFuture<SendResult<String, Customer>> getFutureObject()
      throws InterruptedException, ExecutionException, TimeoutException {
    SettableListenableFuture<SendResult<String, Customer>> future =
        mock(SettableListenableFuture.class);
    when(future.get(30000, TimeUnit.MILLISECONDS)).thenReturn(null);
    return future;
  }

  private String getToken() throws Exception {
    return "bearer " + obtainAccessToken("bill", "abc123");
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
