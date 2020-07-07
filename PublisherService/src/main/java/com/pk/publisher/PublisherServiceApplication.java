package com.pk.publisher;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PublisherServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PublisherServiceApplication.class, args);
  }

}
