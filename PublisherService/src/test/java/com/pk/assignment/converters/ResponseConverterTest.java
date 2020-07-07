package com.pk.assignment.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import com.pk.publisher.converters.ResponseConverter;
import com.pk.publisher.model.CustomerResponse;

public class ResponseConverterTest {

  private ResponseConverter responseConverter;

  @Before
  public void setup() {
    responseConverter = new ResponseConverter();
  }

  @Test
  public void testConvertWhenPassingValidStringMessageShouldReturnSuccessResponseObject() {
    String message = "Successfully Added";
    CustomerResponse response = responseConverter.convert(message);
    assertNotNull(response);
    assertEquals("Successfully Added", response.getMessage());
    assertEquals("SUCCESS", response.getStatus());
  }

}
